package com.app.backend.exceptions.domain;

import com.app.backend.exceptions.AppCustomException;
import com.app.backend.exceptions.AppCustomExceptionStatus;

public class ReservationNotFittingException extends AppCustomException {

  public ReservationNotFittingException() {
    super("Reservation does not fit inside availability", AppCustomExceptionStatus.BAD_REQUEST);
  }
}
