package de.htwberlin.archivewizard.refreshtoken;

import de.htwberlin.archivewizard.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * RefreshTokenRotationResult
 */
public record RefreshTokenRotationResult(@NotBlank String token, @NotNull User user) {
}
