package de.htwberlin.archivewizard.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TemplateUserDetailsService implements UserDetailsService {
  private UserRepository userRepository;

  public TemplateUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) {
    String normalizedEmail = email.trim().toLowerCase();
    User user = userRepository.findByEmailIgnoreCase(normalizedEmail).orElseThrow(
        () -> new UsernameNotFoundException("No User found with the E-Mail: " + email));
    return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
        .password(user.getPasswordHash()).authorities(user.getAuthority().toAuthority()).build();
  }

}
