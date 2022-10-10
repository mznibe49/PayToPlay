package com.app.backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Builder
@Getter
public class HttpException {

  private final HttpStatus status;
  private final String message;
  private final String error;
  private final String path;

  public ResponseEntity<HttpException> toResponseEntity() {
    return new ResponseEntity<>(this, status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
