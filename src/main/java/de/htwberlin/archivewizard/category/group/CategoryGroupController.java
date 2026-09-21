package de.htwberlin.archivewizard.category.group;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<CategoryGroup> createCategoryGroup(
            @RequestBody CreateCategoryGroupRequest categoryGroup,
            @AuthenticationPrincipal Jwt decodedJwt) {

        try {

            return ResponseEntity.status(HttpStatus.CREATED).body(categoryGroupService
                    .createCategoryGroup(categoryGroup, decodedJwt.getClaim("uid")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/get-category-groups")
    public ResponseEntity<List<CategoryGroup>> getCategoryGroup(
            @AuthenticationPrincipal Jwt decodedJwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(categoryGroupService.getCategoryGroups(decodedJwt.getClaim("uid")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
