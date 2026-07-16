package com.yashdotdev.auth_service.exceptions;

public class InvalidTokenRequest extends RuntimeException {
    public InvalidTokenRequest(String message) {
        super(message);
    }
}
