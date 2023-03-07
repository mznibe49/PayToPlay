package com.app.backend.exceptions.domain;

import com.app.backend.exceptions.AppCustomException;
import com.app.backend.exceptions.AppCustomExceptionStatus;

public class ForbiddenOperationException extends AppCustomException {

  public ForbiddenOperationException() {
    super("Forbidden operation", AppCustomExceptionStatus.FORBIDDEN);
  }
}
