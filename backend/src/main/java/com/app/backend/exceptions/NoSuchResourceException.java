package com.app.backend.exceptions;

public abstract class NoSuchResourceException extends AppCustomException {

  protected NoSuchResourceException(long id, String resource) {
    super("No " + resource + " with id " + id + " found", AppCustomExceptionStatus.NOT_FOUND);
  }
}
