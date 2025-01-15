package com.example.project.exception;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String message) {
        super(message);
    }
}