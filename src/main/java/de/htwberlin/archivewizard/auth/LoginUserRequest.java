package de.htwberlin.archivewizard.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request payload used to authenticate an existing user.
 *
 * <p>
 * The record contains the email address and password submitted during login. The fields are
 * validated before the authentication flow continues.
 * </p>
 */
public record LoginUserRequest(
        @NotBlank(message = "E-Mail must not be blank") @Email(
                message = "E-Mail String must be a valid") String email,
        @NotBlank(message = "Password must not be blank") String password) {

}
