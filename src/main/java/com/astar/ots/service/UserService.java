package com.astar.ots.service;

import com.astar.ots.entity.User;
import com.astar.ots.exception.AuthenticationException;
import com.astar.ots.repository.UserRepository;
import com.astar.ots.security.JwtTokenProvider;
import com.astar.ots.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  public String signIn(String username, String password) {

    if (userRepository.existsByUsername(username)) {

      try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
      } catch (org.springframework.security.core.AuthenticationException e) {
        throw new AuthenticationException(Constants.ErrorMessages.INVALID_CREDENTIALS);
      }

    } else {
      throw new AuthenticationException(Constants.ErrorMessages.USER_NOT_EXISTS);
    }
  }

  public void signup(User user) {

    if (!userRepository.existsByUsername(user.getUsername())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
    } else {
      throw new AuthenticationException(Constants.ErrorMessages.USERNAME_ALREADY_USE);
    }
  }
}
