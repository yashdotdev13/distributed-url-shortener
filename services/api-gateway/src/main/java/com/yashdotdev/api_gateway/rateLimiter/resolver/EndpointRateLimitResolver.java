package com.yashdotdev.api_gateway.rateLimiter.resolver;

import org.springframework.http.server.reactive.ServerHttpRequest;
import java.util.Optional;

public interface EndpointRateLimitResolver {

    Optional<EndpointRateLimit> resolve(
            ServerHttpRequest request
    );

}