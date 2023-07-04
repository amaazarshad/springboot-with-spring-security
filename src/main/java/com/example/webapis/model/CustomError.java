package com.example.webapis.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CustomError {
    private Date timestamp;
    private int status;
    private String error;
    private String message;
}
