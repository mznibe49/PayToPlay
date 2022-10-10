package com.app.backend.exceptions;

public class InternalException extends GiskardException {

  private InternalException(String message) {
    super(message, GiskardExceptionStatus.INTERNAL_SERVER_ERROR);
  }

  public static InternalException withMessage(String message) {
    return new InternalException(message);
  }
}
