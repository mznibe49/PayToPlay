package com.app.backend.exceptions.domain;

import com.app.backend.exceptions.GiskardException;
import com.app.backend.exceptions.GiskardExceptionStatus;
public class OverlappingReservationException extends GiskardException {

  public OverlappingReservationException() {
    super(
        "The given reservation is overlapping an existing one", GiskardExceptionStatus.BAD_REQUEST);
  }
}
