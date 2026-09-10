package de.htwberlin.archivewizard.refreshtoken;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.htwberlin.archivewizard.auth.LoginUserRequest;
import de.htwberlin.archivewizard.user.User;
import de.htwberlin.archivewizard.user.UserRepository;

@Service
public class RefreshTokenService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
            UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    // Return this to the client, never store raw
    public String issue(User user) {
        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        RefreshToken newToken =
                new RefreshToken(hash(rawToken), user, Instant.now().plus(30, ChronoUnit.DAYS));
        refreshTokenRepository.save(newToken);

        return rawToken;
    }

    @Transactional
    public RefreshTokenRotationResult rotate(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        RefreshToken stored = refreshTokenRepository.findByHash(hash(rawToken))
                .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));

        if (stored.getIsRevoked()) {
            // token reuse after rotation means likely theft
            // nuke every session for this user
            refreshTokenRepository.deleteAllByUser(stored.getUser());
            throw new InvalidRefreshTokenException("Refresh token reuse detected");
        }
        if (stored.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException("Refresh token expired");
        }

        stored.setIsRevoked(true);
        stored.setRevokedAt(Instant.now());
        refreshTokenRepository.save(stored);
        return new RefreshTokenRotationResult(issue(stored.getUser()), stored.getUser());
    }

    @Transactional
    public void revokeSingleToken(String rawToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByHash(hash(rawToken))
                .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));
        refreshToken.setIsRevoked(true);
        refreshToken.setRevokedAt(Instant.now());
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void revokeAllForUser(User user) {
        List<RefreshToken> refreshTokenList = refreshTokenRepository.findAllByUser(user);
        refreshTokenList.forEach((refreshToken) -> {
            refreshToken.setIsRevoked(true);
            refreshToken.setRevokedAt(Instant.now());
        });
        refreshTokenRepository.saveAll(refreshTokenList);
    }

    public String createRefreshToken(LoginUserRequest loginUserData) {
        User user = userRepository.findByEmailIgnoreCase(loginUserData.email()).orElseThrow();
        return issue(user);
    }

    public String buildRefreshCookie(String rawToken) {
        return ResponseCookie.from("refresh_token", rawToken).httpOnly(true).secure(true)
                .path("/auth-manager").maxAge(Duration.ofDays(30)).sameSite("Strict").build()
                .toString();
    }

    private String hash(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
