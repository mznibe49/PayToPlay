package com.app.backend.exceptions;

public enum AppCustomExceptionStatus {
  BAD_REQUEST("Bad Request"),
  NOT_FOUND("Not Found"),
  FORBIDDEN("Forbidden"),
  INTERNAL_SERVER_ERROR("Internal Server Error");

  private final String representation;

  AppCustomExceptionStatus(String representation) {
    this.representation = representation;
  }

  public String getRepresentation() {
    return this.representation;
  }
}
