package com.jember.alfredo.prueba.controller.exception;

import com.jember.alfredo.prueba.dto.Error;
import com.jember.alfredo.prueba.dto.ErrorResponse;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<ErrorResponse> handleMissingHeader(MissingRequestHeaderException ex) {
    ErrorResponse response =
        new ErrorResponse(
            List.of(
                new Error(
                    "401",
                    "The request has not been applied because it lacks valid authentication credentials for the target resource.")));
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
    List<Error> errors =
        ex.getBindingResult().getAllErrors().stream()
            .map(
                error ->
                    new Error(
                        "400",
                        "The service cannot or will not process the request due to something that is perceived to be a client error"))
            .toList();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex) {
    ErrorResponse response =
        new ErrorResponse(
            List.of(
                new Error(
                    "403",
                    ex.getMessage() != null
                        ? ex.getMessage()
                        : "You are not allowed to use this endpoint!")));
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }

  @ExceptionHandler({DataAccessException.class, RedisConnectionFailureException.class})
  public ResponseEntity<ErrorResponse> handleServiceUnavailable(Exception ex) {
    ErrorResponse response =
        new ErrorResponse(
            List.of(
                new Error(
                    "503",
                    "This service or another required system is currently unable to handle the request.")));
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
  }
}
