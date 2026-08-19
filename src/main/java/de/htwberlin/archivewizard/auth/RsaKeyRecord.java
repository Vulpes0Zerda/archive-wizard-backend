package de.htwberlin.archivewizard.auth;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rsa")
public record RsaKeyRecord(RSAPrivateKey privateKey, RSAPublicKey publicKey) {

}
