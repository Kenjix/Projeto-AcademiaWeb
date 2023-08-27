package com.example.academia.exceptions;

import com.example.academia.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Usuario inexistente", ex.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}