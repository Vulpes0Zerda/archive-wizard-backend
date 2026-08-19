package de.htwberlin.archivewizard.user;

import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  BCryptPasswordEncoder passwordEncoder;


  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = new BCryptPasswordEncoder(16);
  }

  public void createUser(RegisterUserRecord registerUserRecord) throws Exception {
    User user = new User(registerUserRecord.name(), registerUserRecord.email(),
        this.passwordEncoder(registerUserRecord.password()));
    userRepository.save(user);
  }

  public void loginUser(LoginUserRecord loginUserRecord) throws Exception {
    // retrives the user from the db
    Optional<User> user = userRepository.findUserByEmail(loginUserRecord.email());

    // checks if user exists
    // TODO: make a error handler for all optional types
    if (user.isEmpty()) {
      throw new RuntimeException(
          "No user with the e-mail: " + loginUserRecord.email() + "was found.");
    }

    // checks if hashes match
    if (passwordMatches(loginUserRecord.password(), user.get().getPasswordHash())) {

    } else {
      throw new RuntimeException("Entered Password does not match saved one.");
    }
  }

  private String passwordEncoder(String password) throws Exception {
    return passwordEncoder.encode(password);
  }

  private Boolean passwordMatches(String enteredPasswordString, String recivedPasswordHash) {
    return passwordEncoder.matches(enteredPasswordString, recivedPasswordHash);
  }

}
