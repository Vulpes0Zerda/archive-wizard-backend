package de.htwberlin.archivewizard.category.group;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryGroupRecord(@NotBlank(message = "Name must not be blank") String name) {    
}