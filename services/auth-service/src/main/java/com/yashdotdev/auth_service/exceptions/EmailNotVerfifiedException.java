package com.yashdotdev.auth_service.exceptions;

public class EmailNotVerfifiedException extends RuntimeException {
    public EmailNotVerfifiedException(String message) {
        super(message);
    }
}
