package de.htwberlin.archivewizard.category.group;

import jakarta.validation.constraints.NotBlank;


public record CreateCategoryGroupRequest(
        @NotBlank(message = "Name must not be blank") String name) {
}
