package de.htwberlin.archivewizard.shelf;

import de.htwberlin.archivewizard.category.group.CategoryGroup;
import de.htwberlin.archivewizard.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CreateShelfRecord(@NotEmpty(message = "User must not be blank") User user,
    @NotEmpty(message = "Category group must not be blank") CategoryGroup categoryGroup,
    @NotBlank(message = "Name must not be blank") String name,
    @NotEmpty(message = "Position must not be blank") Short position) {

}
