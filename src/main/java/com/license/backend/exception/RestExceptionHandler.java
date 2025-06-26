package com.license.backend.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(InaccessibleException.class)
    public ResponseEntity<?> handleInaccessibleException(InaccessibleException inaccessibleException) {
        RestExceptionDetails exceptionDetails = RestExceptionDetails.of(HttpStatus.FORBIDDEN, inaccessibleException);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FailedLoginException.class)
    public ResponseEntity<?> handleFailedLoginException(FailedLoginException failedLoginException) {
        RestExceptionDetails exceptionDetails = RestExceptionDetails.of(HttpStatus.BAD_REQUEST, failedLoginException);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

}
