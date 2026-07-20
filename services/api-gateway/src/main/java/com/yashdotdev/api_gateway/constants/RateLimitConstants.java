package com.yashdotdev.api_gateway.constants;

public final class RateLimitConstants {

    private RateLimitConstants() {}

    public static final String RATE_LIMIT_PREFIX = "rate_limit:";

    public static final long MAX_REQUESTS = 100;

    public static final long WINDOW_DURATION_SECONDS = 60;

    public static final String TOO_MANY_REQUESTS =
            "Too many requests. Please try again later.";
}