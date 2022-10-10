package com.app.backend.exceptions.domain;

import com.app.backend.exceptions.GiskardException;
import com.app.backend.exceptions.GiskardExceptionStatus;

public class ReservationNotFittingException extends GiskardException {

  public ReservationNotFittingException() {
    super("Reservation does not fit inside availability", GiskardExceptionStatus.BAD_REQUEST);
  }
}
