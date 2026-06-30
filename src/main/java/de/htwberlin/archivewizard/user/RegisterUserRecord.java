package de.htwberlin.archivewizard.user;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserRecord(@NotBlank(message = "Name must not be blank") String name,
    @NotBlank(message = "Email must not be blank") String email,
    @NotBlank(message = "Password must not be blank") String password) {
}
