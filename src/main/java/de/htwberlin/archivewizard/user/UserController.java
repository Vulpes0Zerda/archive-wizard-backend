package de.htwberlin.archivewizard.user;

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
    userService.createUser(registerUserRecord);
    return ResponseEntity.ok().build();
  }

}
