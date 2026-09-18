package de.htwberlin.archivewizard.item;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
@RequestMapping (value = "/item-manager")

public class ItemController {

  private ItemService itemService;

  public ItemController(ItemService itemService){
    this.itemService = itemService;
  }

  @GetMapping("/get-all-items/{shelfId}")
  public ResponseEntity<List<Item>> getAllItems(@AuthenticationPrincipal Jwt decodedJwt, @RequestParam long shelfId) {

    try {
      return ResponseEntity.status(HttpStatus.OK).body(itemService.getAllItems(decodedJwt.getClaim("uid"), shelfId));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
  
  
}
