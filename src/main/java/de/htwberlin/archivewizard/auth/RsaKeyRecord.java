package de.htwberlin.archivewizard.auth;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Holds the RSA key pair used for signing and validating JWT tokens.
 *
 * <p>The private key is used to create signed access tokens, while the public key is used to verify
 * the signatures during resource-server authentication.</p>
 */
@ConfigurationProperties(prefix = "rsa")
public record RsaKeyRecord(RSAPrivateKey privateKey, RSAPublicKey publicKey) {

}
