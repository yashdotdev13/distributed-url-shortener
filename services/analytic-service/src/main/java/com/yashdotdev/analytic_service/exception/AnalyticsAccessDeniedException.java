package com.yashdotdev.analytic_service.exception;


public class AnalyticsAccessDeniedException
        extends RuntimeException {

    public AnalyticsAccessDeniedException(
            String shortCode
    ) {
        super(
                "You are not authorized to access analytics for short code : "
                        + shortCode
        );
    }
}
