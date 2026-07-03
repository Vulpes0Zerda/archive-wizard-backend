package de.htwberlin.archivewizard.category.group;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import de.htwberlin.archivewizard.shelf.ShelfService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/category-group-manager")
public class CategoryGroupController {
    private final CategoryGroupService categoryGroupService;

    public CategoryGroupController(CategoryGroupService categoryGroupService) {
        this.categoryGroupService = categoryGroupService;
    }

    @PostMapping("/create-category-group")
    public ResponseEntity<List<CategoryGroup>> createCategoryGroup(@RequestBody CreateCategoryGroupRecord categoryGroup) {
        List<CategoryGroup> savedCategoryGroups = categoryGroupService.createCategoryGroup(categoryGroup);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(savedCategoryGroups);
    }
    
    
}
