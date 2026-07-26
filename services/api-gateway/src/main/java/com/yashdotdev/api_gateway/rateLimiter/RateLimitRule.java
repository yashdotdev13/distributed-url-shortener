package com.yashdotdev.api_gateway.rateLimiter;


import lombok.Builder;

@Builder
public record RateLimitRule (


        Long capacity,
        Long refillTokens,
        Long refillDurationSeconds
){}
