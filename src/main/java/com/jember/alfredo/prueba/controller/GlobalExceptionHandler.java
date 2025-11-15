package com.jember.alfredo.prueba.controller;

import com.jember.alfredo.prueba.dto.Error;
import com.jember.alfredo.prueba.dto.ErrorResponse;
import java.util.List;
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
            List.of(new Error("401", "There are no valid authentication credentials present.")));
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
    List<Error> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                error ->
                    new Error(
                        "400",
                        String.format(
                            "Field '%s': %s", error.getField(), error.getDefaultMessage())))
            .toList();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
  }
}
