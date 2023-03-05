package com.example.demo.exception;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleNoElementFound(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleInvalidToken(MalformedJwtException ex) {
        return new ResponseEntity<>("Invalid credentials", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }
}
