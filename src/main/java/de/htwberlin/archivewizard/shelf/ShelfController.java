package de.htwberlin.archivewizard.shelf;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "/shelf-manager")
@CrossOrigin(origins = "http://localhost:4200")
public class ShelfController {

  private final ShelfService shelfService;

  public ShelfController(ShelfService shelfService) {
    this.shelfService = shelfService;
  }

  @GetMapping("/get-overview")
  public ResponseEntity<List<Shelf>> getAllShelfs(@AuthenticationPrincipal Jwt decodedJwt) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(this.shelfService.getAllShelfs(decodedJwt.getClaim("uid")));
  }

  @PostMapping("/create-shelf")
  public ResponseEntity<Shelf> createShelf(@RequestBody CreateShelfRequest shelf,
      @AuthenticationPrincipal Jwt decodedJwt) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(shelfService.createShelf(shelf, decodedJwt.getClaim("uid")));
  }

}
