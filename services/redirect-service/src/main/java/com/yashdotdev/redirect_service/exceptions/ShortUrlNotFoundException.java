package com.yashdotdev.redirect_service.exceptions;

public class ShortUrlNotFoundException extends RuntimeException {

    public ShortUrlNotFoundException(String shortCode) {
        super("Short URL not found : " + shortCode);
    }

}