package com.example.licenta.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GeneralAdminException extends Exception {
    private String message;
    private HttpStatus status;

    public GeneralAdminException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.status = httpStatus;
    }
}
