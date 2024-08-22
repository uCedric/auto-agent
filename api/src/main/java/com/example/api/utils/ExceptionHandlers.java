package com.example.api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.api.utils.Exceptions.DataFormateException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(DataFormateException.class)
    public ResponseEntity<ErrorResponse> handleDataFormateException(DataFormateException error) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), error.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
