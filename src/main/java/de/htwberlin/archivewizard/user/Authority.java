package de.htwberlin.archivewizard.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Authority implements GrantedAuthority {
  USER, ADMIN;

  public GrantedAuthority toAuthority() {
    return new SimpleGrantedAuthority(getAuthority());

  }

  @Override
  public String getAuthority() {
    return "ROLE_" + name();
  }
}
