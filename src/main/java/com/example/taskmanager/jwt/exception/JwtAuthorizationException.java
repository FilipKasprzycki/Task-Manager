package com.example.taskmanager.jwt.exception;

public class JwtAuthorizationException extends RuntimeException{

    public JwtAuthorizationException(String message) {
        super(message);
    }
}
