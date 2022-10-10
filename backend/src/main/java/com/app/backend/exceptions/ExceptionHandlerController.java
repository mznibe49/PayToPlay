package com.app.backend.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerController {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

  private static final String INTERNAL_ERROR_MESSAGE = "Something went wrong";

  @ExceptionHandler({Exception.class})
  public ResponseEntity<HttpException> handleNoneGiskardException(
      Exception exception, WebRequest request) {
    logger.error("Unexpected error", exception);

    return HttpException.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .message(INTERNAL_ERROR_MESSAGE)
        .error(GiskardExceptionStatus.INTERNAL_SERVER_ERROR.getRepresentation())
        .path(request.getDescription(false).substring(4))
        .build()
        .toResponseEntity();
  }

  @ExceptionHandler({GiskardException.class})
  public ResponseEntity<HttpException> handleGiskardException(
      GiskardException exception, WebRequest request) {
    logger.error(exception.getMessage());

    // Hide message before sending it to the client
    String message =
        (exception instanceof InternalException) ? INTERNAL_ERROR_MESSAGE : exception.getMessage();

    return HttpException.builder()
        .status(getHttpStatus(exception))
        .message(message)
        .error(exception.getStatus().getRepresentation())
        .path(request.getDescription(false).substring(4))
        .build()
        .toResponseEntity();
  }

  private HttpStatus getHttpStatus(GiskardException exception) {
    GiskardExceptionStatus status = exception.getStatus();
    if (status == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    switch (status) {
      case BAD_REQUEST:
        return HttpStatus.BAD_REQUEST;
      case NOT_FOUND:
        return HttpStatus.NOT_FOUND;
      case FORBIDDEN:
        return HttpStatus.FORBIDDEN;
      case INTERNAL_SERVER_ERROR:
      default:
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }
}
