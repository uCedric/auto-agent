package com.example.api.utils;

import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.api.utils.Exceptions.InternalServerException;
import com.example.api.utils.Exceptions.InvalidParameterException;
import com.example.api.utils.Exceptions.ExternalServerException;
import com.example.api.utils.Exceptions.ForbiddenException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ExternalServerException.class)
    public ResponseEntity<ErrorResponse> handleExternalServerException(ExternalServerException error) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponse> handleDataFormateException(InvalidParameterException error) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), error.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(InternalServerException error) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidenException(ForbiddenException error) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), error.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<ErrorResponse> handleExecutionException(ExecutionException error) {
        Throwable cause = error.getCause();

        ErrorResponse errorResponse = new ErrorResponse();
        if (cause instanceof InvalidParameterException) {
            InvalidParameterException invalidParamEx = (InvalidParameterException) cause;

            errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            errorResponse.setMessage(invalidParamEx.getMessage());
        } else if (cause instanceof InternalServerException) {
            InternalServerException internalServerEx = (InternalServerException) cause;

            errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.setMessage(internalServerEx.getMessage());
        } else {
            errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.setMessage("Internal Server Error");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
