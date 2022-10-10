package com.app.backend.exceptions.domain;

import com.app.backend.exceptions.NoSuchResourceException;
public class NoSuchAvailabilityException extends NoSuchResourceException {

  public NoSuchAvailabilityException(long id) {
    super(id, "availability");
  }
}
