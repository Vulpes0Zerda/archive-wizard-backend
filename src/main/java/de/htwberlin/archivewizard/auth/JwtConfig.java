package de.htwberlin.archivewizard.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class JwtConfig {

  /**
   * Returns a JWT Encoder object that encodes the JWT Tokes with the RSA Keys.
   * 
   * @param rsaKeys the pair of RSA Keys created on the server
   * @return the NimbusJwtEncoder
   * @see NimbusJwtEncoder
   */
  @Bean
  public JwtEncoder jwtEncoder(RsaKeyRecord rsaKeys) {
    JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwkSource);
  }

  @Bean
  public JwtDecoder jwtDecoder(RsaKeyRecord rsaKeys) {
    return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
  }
}
