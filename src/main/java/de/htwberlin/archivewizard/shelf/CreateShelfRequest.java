package de.htwberlin.archivewizard.shelf;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateShelfRequest(@NotBlank(message = "Name must not be blank") String name,
    @NotNull(message = "Position must not be empty") Short position,
    @NotEmpty(message = "Category Group Id must not be empty") Long categoryGroupId) {

}
