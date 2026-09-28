package de.htwberlin.archivewizard.refreshtoken;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import de.htwberlin.archivewizard.user.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByHash(String hash);

  long deleteAllByUser(User user);

  List<RefreshToken> findAllByUser(User user);

  @Modifying
  @Query("DELETE FROM RefreshToken t WHERE t.expiresAt < :now OR (t.isRevoked = true AND t.revokedAt < :revokedCutoff)")
  long deleteStale(@Param("now") Instant now, @Param("revokedCutoff") Instant revokedCutoff);
}
