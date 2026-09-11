package de.htwberlin.archivewizard.refreshtoken;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

@Component
public class RefreshTokenCleanupTask {
  private final RefreshTokenRepository refreshTokenRepository;

  public RefreshTokenCleanupTask(RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
  }

  // 3am once a day
  @Scheduled(cron = "0 0 3 * * *")
  @Transactional
  public void cleanup() {
    refreshTokenRepository.deleteStale(Instant.now(), Instant.now().minus(2, ChronoUnit.DAYS));
  }
}
