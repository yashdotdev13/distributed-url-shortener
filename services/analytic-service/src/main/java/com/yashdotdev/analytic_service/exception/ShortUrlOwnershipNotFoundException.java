package com.yashdotdev.analytic_service.exception;

public class ShortUrlOwnershipNotFoundException
        extends RuntimeException {

    public ShortUrlOwnershipNotFoundException(
            String shortCode
    ) {
        super(
                "Ownership not found for short code : "
                        + shortCode
        );
    }
}