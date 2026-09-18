package de.htwberlin.archivewizard.item;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.htwberlin.archivewizard.auth.AuthController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller 
@RequestMapping (value = "/item-manager")

public class ItemController {

  private ItemService itemService;
  private final Logger logger = LoggerFactory.getLogger(AuthController.class);

  public ItemController(ItemService itemService){
    this.itemService = itemService;
  }

  @GetMapping("/get-all-items")
  @ResponseBody 
public ResponseEntity<List<Item>> getAllItems(@AuthenticationPrincipal Jwt decodedJwt, @RequestParam Long shelfId) {

    try {
      return ResponseEntity.status(HttpStatus.OK).body(this.itemService.getAllItems(decodedJwt.getClaim("uid"), shelfId));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("path")
  public String postMethodName(@RequestBody String entity) {
      //TODO: process POST request
      
      return entity;
  }
  
  
  
}
