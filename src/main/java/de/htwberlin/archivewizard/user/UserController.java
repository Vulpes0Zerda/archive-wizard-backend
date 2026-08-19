package de.htwberlin.archivewizard.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user-manager")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register-user")
  public ResponseEntity<String> createUser(@RequestBody RegisterUserRecord registerUserRecord) {
    try {
      userService.createUser(registerUserRecord);
      return ResponseEntity.ok().build();

    } catch (Exception e) {
      // TODO: create and return meaningful errors
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

}
