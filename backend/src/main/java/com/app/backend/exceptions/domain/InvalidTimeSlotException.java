package com.app.backend.exceptions.domain;


import com.app.backend.exceptions.AppCustomException;
import com.app.backend.exceptions.AppCustomExceptionStatus;

public class InvalidTimeSlotException extends AppCustomException {

  public InvalidTimeSlotException(String str) {
    super(str, AppCustomExceptionStatus.BAD_REQUEST);
  }
}
