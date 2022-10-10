package com.app.backend.exceptions.domain;


import com.app.backend.exceptions.GiskardException;
import com.app.backend.exceptions.GiskardExceptionStatus;

public class InvalidTimeSlotException extends GiskardException {

  public InvalidTimeSlotException(String str) {
    super(str, GiskardExceptionStatus.BAD_REQUEST);
  }
}
