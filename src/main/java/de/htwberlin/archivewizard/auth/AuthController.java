package de.htwberlin.archivewizard.auth;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import de.htwberlin.archivewizard.accesstoken.AccessTokenService;
import de.htwberlin.archivewizard.refreshtoken.RefreshTokenRotationResult;
import de.htwberlin.archivewizard.refreshtoken.RefreshTokenService;
import de.htwberlin.archivewizard.user.UserService;
import jakarta.validation.Valid;

/**
 * Authentication endpoint controller.
 *
 * <p>
 * The controller currently exposes registration and will later handle sign-in requests as part of
 * the JWT-based authentication flow. The registration endpoint delegates to the user service while
 * the login endpoint is still under implementation.
 * </p>
 */
@Controller
@RequestMapping("/auth-manager")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
  private final UserService userService;
  private final RefreshTokenService refreshTokenService;
  private final AccessTokenService accessTokenService;
  private final Logger logger = LoggerFactory.getLogger(AuthController.class);

  public AuthController(UserService userService, RefreshTokenService refreshTokenService,
      AccessTokenService accessTokenService) {
    this.userService = userService;
    this.refreshTokenService = refreshTokenService;
    this.accessTokenService = accessTokenService;

  }

  /**
   * Registers a new user through the authentication API.
   *
   * @param registerUserRecord the account data submitted by the client
   * @return 200 OK on success, otherwise 400 BAD REQUEST
   */
  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> register(
      @RequestBody @Valid RegisterUserRequest registerUserData) {
    try {
      userService.createUser(registerUserData);

      LoginUserRequest loginUserData =
          new LoginUserRequest(registerUserData.email(), registerUserData.password());

      return ResponseEntity.status(HttpStatus.OK)
          .header(HttpHeaders.SET_COOKIE,
              refreshTokenService
                  .buildRefreshCookie(refreshTokenService.createRefreshToken(loginUserData)))
          .body(accessTokenService.createAccessToken(loginUserData));

    } catch (Exception e) {
      logger.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(
      @RequestBody @Valid LoginUserRequest loginUserData) {
    try {
      return ResponseEntity.status(HttpStatus.OK)
          .header(HttpHeaders.SET_COOKIE,
              refreshTokenService
                  .buildRefreshCookie(refreshTokenService.createRefreshToken(loginUserData)))
          .body(accessTokenService.createAccessToken(loginUserData));
    } catch (Exception e) {
      logger.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/refresh")
  public ResponseEntity<Map<String, String>> refresh(
      @CookieValue("refresh_token") String refreshCookie) {
    RefreshTokenRotationResult refreshTokenRotationResult =
        refreshTokenService.rotate(refreshCookie);
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.SET_COOKIE,
            refreshTokenService.buildRefreshCookie(refreshTokenRotationResult.token()))
        .body(accessTokenService.refreshAccessToken(refreshTokenRotationResult));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@CookieValue("refresh_token") String refreshCookie) {
    try {
      refreshTokenService.revokeSingleToken(refreshCookie);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      logger.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

}
