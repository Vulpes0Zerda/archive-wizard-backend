package de.htwberlin.archivewizard.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // todo: don't return User, return jwt auth token instead
  public User createUser(RegisterUserRecord registerUserRecord) {
    User user = new User(registerUserRecord.name(), registerUserRecord.email(),
        registerUserRecord.password());
    userRepository.save(user);
    return user;
  }

}
