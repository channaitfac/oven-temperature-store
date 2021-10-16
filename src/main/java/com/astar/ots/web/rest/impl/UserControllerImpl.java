package com.astar.ots.web.rest.impl;

import com.astar.ots.service.UserService;
import com.astar.ots.util.Constants;
import com.astar.ots.web.request.UserSignInRequest;
import com.astar.ots.web.rest.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = Constants.FORWARD_SLASH + Constants.RestUrls.USERS)
public class UserControllerImpl implements UserController {

  @Autowired
  private Validator validator;

  @Autowired
  private UserService userService;

  public ResponseEntity login(@RequestBody UserSignInRequest userSignInRequest) {

    try {

      // Request parameter validation
      doValidate(userSignInRequest);

      // Generate token
      final String token = userService.signIn(userSignInRequest.getUsername(), userSignInRequest.getPassword());
      return new ResponseEntity<>(token, HttpStatus.OK);

    } catch (Exception e) {
      log.error("User authentication error: {}", e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  private void doValidate(UserSignInRequest userSignInRequest) {
    Set<ConstraintViolation<UserSignInRequest>> violations = validator.validate(userSignInRequest);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
