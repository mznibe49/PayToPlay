package com.app.backend.exceptions;

public abstract class GiskardException extends RuntimeException {

  private final GiskardExceptionStatus status;

  protected GiskardException(final String message, GiskardExceptionStatus status) {
    super(message);
    this.status = status;
  }

  public GiskardExceptionStatus getStatus() {
    return status;
  }
}
