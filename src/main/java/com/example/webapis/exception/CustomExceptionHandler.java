package com.example.webapis.exception;

import com.example.webapis.model.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> handleException(Exception ex) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // Default status

        // Determine the status based on the exception type
        if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        }

        CustomError customError = CustomError.builder()
                .timestamp(new Date())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(status).body(customError);
    }
}
