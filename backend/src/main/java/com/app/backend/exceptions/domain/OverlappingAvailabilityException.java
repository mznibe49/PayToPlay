package com.app.backend.exceptions.domain;

import com.app.backend.exceptions.AppCustomException;
import com.app.backend.exceptions.AppCustomExceptionStatus;
public class OverlappingAvailabilityException extends AppCustomException {

  public OverlappingAvailabilityException() {
    super(
        "The given availability is overlapping an existing one",
        AppCustomExceptionStatus.BAD_REQUEST);
  }
}
