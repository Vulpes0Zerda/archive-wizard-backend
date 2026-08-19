package de.htwberlin.archivewizard.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRecord(
        @NotBlank(message = "E-Mail must not be blank") @Email(
                message = "E-Mail String must be a valid") String email,
        @NotBlank(message = "Password must not be blank") String password) {

}
