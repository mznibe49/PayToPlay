package com.app.backend.exceptions;

public abstract class AppCustomException extends RuntimeException {

  private final AppCustomExceptionStatus status;

  protected AppCustomException(final String message, AppCustomExceptionStatus status) {
    super(message);
    this.status = status;
  }

  public AppCustomExceptionStatus getStatus() {
    return status;
  }
}
