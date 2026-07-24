package com.yashdotdev.redirect_service.exceptions;


public class UrlExpiredException extends RuntimeException {

    public UrlExpiredException(String shortCode) {
        super("Short URL has expired : " + shortCode);
    }

}