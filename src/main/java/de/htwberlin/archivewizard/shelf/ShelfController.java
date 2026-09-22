package de.htwberlin.archivewizard.shelf;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = "/shelf-manager")
public class ShelfController {

  private final ShelfService shelfService;

  private final Logger logger = LoggerFactory.getLogger(ShelfController.class);

  public ShelfController(ShelfService shelfService) {
    this.shelfService = shelfService;
  }

  @GetMapping("/get-overview")
  public ResponseEntity<List<Shelf>> getAllShelfs(@AuthenticationPrincipal Jwt decodedJwt) {
    try {
      return ResponseEntity.status(HttpStatus.OK)
          .body(this.shelfService.getAllShelfs(decodedJwt.getClaim("uid")));

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/create-shelf")
  public ResponseEntity<Shelf> createShelf(@RequestBody CreateShelfRequest shelf,
      @AuthenticationPrincipal Jwt decodedJwt) {
    try {
      logger.debug("Trying to create Shelf with uid: " + decodedJwt.getClaim("uid") + " and shelf: "
          + shelf.toString());
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(shelfService.createShelf(shelf, decodedJwt.getClaim("uid")));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
