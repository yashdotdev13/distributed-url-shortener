package com.yashdotdev.api_gateway.rateLimiter;


import reactor.core.publisher.Mono;

public interface RateLimiterService {

    Mono<RateLimitResult> allowRequest(
            String key,
            RateLimitRule rule
    );
}