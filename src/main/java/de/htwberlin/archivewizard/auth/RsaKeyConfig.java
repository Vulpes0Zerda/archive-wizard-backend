package de.htwberlin.archivewizard.auth;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor;

@Configuration
@EnableConfigurationProperties(RsaKeyRecord.class)
public class RsaKeyConfig {
  @Bean
  public static RsaKeyConversionServicePostProcessor rsaKeyConversionServicePostProcessor() {
    return new RsaKeyConversionServicePostProcessor();
  }
}
