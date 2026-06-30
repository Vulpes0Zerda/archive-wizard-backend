package de.htwberlin.archivewizard.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void createUser(RegisterUserRecord registerUserRecord) {
    User user = new User(registerUserRecord.name(), registerUserRecord.email(),
        registerUserRecord.password());
    userRepository.save(user);
  }

}
