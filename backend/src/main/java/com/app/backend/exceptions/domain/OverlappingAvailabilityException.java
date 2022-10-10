package com.app.backend.exceptions.domain;

import com.app.backend.exceptions.GiskardException;
import com.app.backend.exceptions.GiskardExceptionStatus;
public class OverlappingAvailabilityException extends GiskardException {

  public OverlappingAvailabilityException() {
    super(
        "The given availability is overlapping an existing one",
        GiskardExceptionStatus.BAD_REQUEST);
  }
}
