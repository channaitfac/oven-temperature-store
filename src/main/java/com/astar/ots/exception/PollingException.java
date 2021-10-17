package com.astar.ots.exception;

public class PollingException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final String message;

  public PollingException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
