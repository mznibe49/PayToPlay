package com.app.backend.exceptions.domain;

import com.app.backend.exceptions.AppCustomException;
import com.app.backend.exceptions.AppCustomExceptionStatus;
public class OverlappingReservationException extends AppCustomException {

  public OverlappingReservationException() {
    super(
        "The given reservation is overlapping an existing one", AppCustomExceptionStatus.BAD_REQUEST);
  }
}
