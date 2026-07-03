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
  public ResponseEntity<User> createUser(@RequestBody RegisterUserRecord user) {
    User savedUser = userService.createUser(user);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(savedUser);
  }

}
