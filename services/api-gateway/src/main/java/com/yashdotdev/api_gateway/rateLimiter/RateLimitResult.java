package com.yashdotdev.api_gateway.rateLimiter;

import lombok.Builder;

@Builder
public record RateLimitResult(

        boolean allowed,

        long remainingTokens,

        long retryAfterSeconds

) {
}