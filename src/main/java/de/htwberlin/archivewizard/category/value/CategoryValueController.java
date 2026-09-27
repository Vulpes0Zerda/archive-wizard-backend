package de.htwberlin.archivewizard.category.value;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category-value-manager")
public class CategoryValueController {

  private final CategoryValueService categoryValueService;
  private final Logger logger = LoggerFactory.getLogger(CategoryValueController.class);

  public CategoryValueController(CategoryValueService categoryValueService) {
    this.categoryValueService = categoryValueService;
  }

  @PatchMapping("/update-category-values")
  public ResponseEntity<List<CategoryValue>> updateCategoryValue(
      @AuthenticationPrincipal Jwt decodedJwt,
      @RequestBody List<UpdateCategoryValueRequest> updateCategoryValueData) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(categoryValueService
          .updateCategoryValues(decodedJwt.getClaim("uid"), updateCategoryValueData));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
