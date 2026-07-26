package com.yashdotdev.api_gateway.resolver;

import org.springframework.http.server.reactive.ServerHttpRequest;
import java.util.Optional;

public interface EndpointRateLimitResolver {

    Optional<EndpointRateLimit> resolve(
            ServerHttpRequest request
    );

}