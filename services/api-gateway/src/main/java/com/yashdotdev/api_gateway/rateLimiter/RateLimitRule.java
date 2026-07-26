package com.yashdotdev.api_gateway.rateLimiter;

import lombok.Data;

@Data
public class RateLimitRule {

    private long capacity;
    private long refillTokens;
    private long refillDurationSeconds;

}