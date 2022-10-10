package com.app.backend.exceptions.domain;

import com.app.backend.exceptions.NoSuchResourceException;
public class NoSuchReservationException extends NoSuchResourceException {

  public NoSuchReservationException(long id) {
    super(id, "reservation");
  }
}
