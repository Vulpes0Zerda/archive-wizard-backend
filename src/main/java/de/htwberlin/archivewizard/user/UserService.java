package de.htwberlin.archivewizard.user;

import org.springframework.stereotype.Service;
import de.htwberlin.archivewizard.auth.RegisterUserRequest;
import de.htwberlin.archivewizard.auth.SecurityConfig;
import jakarta.transaction.Transactional;

/**
 * Handles user account lifecycle operations such as registration and authentication checks.
 *
 * <p>
 * This service coordinates with the persistence layer to store new users and verify submitted
 * credentials against the hashed password stored in the database.
 * </p>
 */
@Service
public class UserService {
  private final UserRepository userRepository;
  private final SecurityConfig securityConfig;

  public UserService(UserRepository userRepository, SecurityConfig securityConfig) {

    this.userRepository = userRepository;
    this.securityConfig = securityConfig;
  }

  /**
   * Creates a new user record and persists it after hashing the supplied password.
   *
   * @param registerUserRecord the validated registration payload from the client
   * @throws Exception if hashing or persistence fails during the registration flow
   */
  @Transactional
  public void createUser(RegisterUserRequest registerUserRecord) throws Exception {
    User user = new User(registerUserRecord.name(), registerUserRecord.email().trim().toLowerCase(),
        this.securityConfig.passwordEncoder().encode(registerUserRecord.password()));
    userRepository.save(user);
  }

}
