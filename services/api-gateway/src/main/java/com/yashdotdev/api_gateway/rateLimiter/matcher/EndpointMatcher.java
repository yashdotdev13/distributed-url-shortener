package com.yashdotdev.api_gateway.rateLimiter.matcher;


import com.yashdotdev.api_gateway.rateLimiter.resolver.EndpointRateLimit;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Optional;

public interface EndpointMatcher {

    Optional<EndpointRateLimit> match(
            ServerHttpRequest request
    );

}