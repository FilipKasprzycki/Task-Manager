package com.example.taskmanager.api.exception;

public class Http400BadRequestException extends RuntimeException {

    public Http400BadRequestException(String message) {
        super(message);
    }
}
