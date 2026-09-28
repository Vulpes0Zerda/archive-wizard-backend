package de.htwberlin.archivewizard.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Input payload for creating a new user account.
 *
 * <p>
 * Validation annotations ensure the request contains a non-empty name, a syntactically valid email
 * address, and a non-empty password before the service layer accepts the data.
 * </p>
 */
public record RegisterUserRequest(@NotBlank(message = "Name must not be blank") String name,
        @NotBlank(message = "E-Mail must not be blank") @Email(
                message = "E-Mail String must be a valid") String email,
        @NotBlank(message = "Password must not be blank") String password) {
}
