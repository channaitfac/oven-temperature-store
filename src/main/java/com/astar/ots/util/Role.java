package com.astar.ots.util;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

  ROLE_ADMIN;

  public String getAuthority() {
    return name();
  }
}
