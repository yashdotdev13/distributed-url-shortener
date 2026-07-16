package com.yashdotdev.auth_service.exceptions;

public class ExpiredTokenRequest extends RuntimeException {
    public ExpiredTokenRequest(String message) {
        super(message);
    }
}
