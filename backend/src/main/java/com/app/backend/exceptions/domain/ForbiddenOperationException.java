package com.app.backend.exceptions.domain;

import com.app.backend.exceptions.GiskardException;
import com.app.backend.exceptions.GiskardExceptionStatus;

public class ForbiddenOperationException extends GiskardException {

  public ForbiddenOperationException() {
    super("Forbidden operation", GiskardExceptionStatus.FORBIDDEN);
  }
}
