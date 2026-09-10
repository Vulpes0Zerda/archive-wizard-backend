package de.htwberlin.archivewizard.accesstoken;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import de.htwberlin.archivewizard.refreshtoken.RefreshTokenRotationResult;
import de.htwberlin.archivewizard.auth.LoginUserRequest;

/**
 * Generates JWT access tokens for authenticated users.
 *
 * <p>
 * The token includes the current user identity, the issuing authority, an expiration timestamp, and
 * a serialized list of granted roles. This allows downstream Spring Security components to
 * recognize which permissions the authenticated principal has.
 * </p>
 */
@Service
public class AccessTokenService {

  private final JwtEncoder jwtEncoder;
  private final AuthenticationManager authenticationManager;


  public AccessTokenService(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
    this.jwtEncoder = jwtEncoder;
    this.authenticationManager = authenticationManager;

  }

  /**
   * Creates a signed JWT for the given authenticated principal.
   *
   * @param authentication the current Spring Security authentication object
   * @return the serialized access token value
   */
  public String generate(String subject, Collection<? extends GrantedAuthority> authorities) {
    Instant now = Instant.now();
    String roles = authorities.stream().map((authority) -> authority.getAuthority())
        .collect(Collectors.joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now)
        .expiresAt(now.plus(15, ChronoUnit.MINUTES)).subject(subject).claim("roles", roles).build();

    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  public Map<String, String> createAccessToken(LoginUserRequest loginUserData) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginUserData.email(), loginUserData.password()));
    return Map.of("accessToken",
        generate(authentication.getName(), authentication.getAuthorities()));
  }

  public Map<String, String> refreshAccessToken(
      RefreshTokenRotationResult refreshTokenRotationResult) {
    return Map.of("accessToken", generate(refreshTokenRotationResult.user().getEmail(),
        Set.of(refreshTokenRotationResult.user().getAuthority())));
  }
}
