package com.example.licenta.exceptions;

public class RequestsLimitReachedException extends Exception {
    public RequestsLimitReachedException(String message){
        super(message);
    }
}
