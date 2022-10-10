package com.app.backend.exceptions;

public enum GiskardExceptionStatus {
  BAD_REQUEST("Bad Request"),
  NOT_FOUND("Not Found"),
  FORBIDDEN("Forbidden"),
  INTERNAL_SERVER_ERROR("Internal Server Error");

  private final String representation;

  GiskardExceptionStatus(String representation) {
    this.representation = representation;
  }

  public String getRepresentation() {
    return this.representation;
  }
}
