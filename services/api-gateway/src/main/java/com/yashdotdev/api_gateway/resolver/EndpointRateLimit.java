package com.yashdotdev.api_gateway.resolver;

import com.yashdotdev.api_gateway.rateLimiter.RateLimitRule;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EndpointRateLimit {

    private String redisKey;
    private RateLimitRule rule;

}