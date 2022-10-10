package com.app.backend.exceptions;

public abstract class NoSuchResourceException extends GiskardException {

  protected NoSuchResourceException(long id, String resource) {
    super("No " + resource + " with id " + id + " found", GiskardExceptionStatus.NOT_FOUND);
  }
}
