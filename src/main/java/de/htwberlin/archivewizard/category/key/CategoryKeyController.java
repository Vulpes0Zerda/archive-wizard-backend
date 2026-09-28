package de.htwberlin.archivewizard.category.key;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/category-key-manager")
public class CategoryKeyController {
  private final CategoryKeyService categoryKeyService;
  private final Logger logger = LoggerFactory.getLogger(CategoryKeyController.class);


  public CategoryKeyController(CategoryKeyService categoryKeyService) {
    this.categoryKeyService = categoryKeyService;
  }

  @GetMapping("/get-category-keys")
  public ResponseEntity<List<CategoryKey>> getCategoryKey(@AuthenticationPrincipal Jwt decodedJwt,
      @RequestParam Long categoryGroupId) {
    try {
      return ResponseEntity.status(HttpStatus.OK)
          .body(categoryKeyService.getCategoryKeys(decodedJwt.getClaim("uid"), categoryGroupId));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
