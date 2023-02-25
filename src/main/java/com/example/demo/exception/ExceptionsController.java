package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleNoElementFound(RuntimeException ex) {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
