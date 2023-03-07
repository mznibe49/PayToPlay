package com.app.backend.exceptions;

public class InternalException extends AppCustomException {

  private InternalException(String message) {
    super(message, AppCustomExceptionStatus.INTERNAL_SERVER_ERROR);
  }

  public static InternalException withMessage(String message) {
    return new InternalException(message);
  }
}
