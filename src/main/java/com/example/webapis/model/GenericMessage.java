package com.example.webapis.model;

import com.example.webapis.WebapisApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GenericMessage {
    void throwErrorMessage(String message){
        throw new IllegalArgumentException(message);
    }

    public IllegalArgumentException errorMessage(String message){
        return new IllegalArgumentException(message);
    }

    public ResponseEntity<?> successMessage(Object body, HttpStatus status){
        return ResponseEntity.status(status).body(body);
    }
}

