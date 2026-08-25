package de.htwberlin.archivewizard.category.group;

import de.htwberlin.archivewizard.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CreateCategoryGroupRecord(@NotEmpty(message = "User must not be empty") User user,  @NotBlank(message = "Name must not be blank") String name) {    
}