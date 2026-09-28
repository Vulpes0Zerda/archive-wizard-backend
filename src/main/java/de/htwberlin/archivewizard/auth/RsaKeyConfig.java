package de.htwberlin.archivewizard.auth;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor;

/**
 * Configures the RSA key pair used by the JWT infrastructure.
 *
 * <p>Spring Boot binds the RSA private and public keys from application properties into the
 * {@link RsaKeyRecord}. The conversion post-processor then makes those keys available to the JWT
 * encoder and decoder.</p>
 */
@Configuration
@EnableConfigurationProperties(RsaKeyRecord.class)
public class RsaKeyConfig {
  @Bean
  public static RsaKeyConversionServicePostProcessor rsaKeyConversionServicePostProcessor() {
    return new RsaKeyConversionServicePostProcessor();
  }
}
