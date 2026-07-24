package com.yashdotdev.url_service.exception;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(Long id) {
        super("URL not found with id : " + id);
    }

}