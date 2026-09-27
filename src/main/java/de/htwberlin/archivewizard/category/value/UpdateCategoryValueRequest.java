package de.htwberlin.archivewizard.category.value;

import jakarta.validation.constraints.NotEmpty;

public record UpdateCategoryValueRequest(@NotEmpty String value, @NotEmpty Long categoryKeyId,
    @NotEmpty Long itemId) {

}
