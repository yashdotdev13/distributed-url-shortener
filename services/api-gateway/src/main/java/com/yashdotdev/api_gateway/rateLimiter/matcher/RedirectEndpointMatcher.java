package com.yashdotdev.api_gateway.rateLimiter.matcher;

import com.yashdotdev.api_gateway.config.RateLimitProperties;
import com.yashdotdev.api_gateway.rateLimiter.RateLimitKeyGenerator;
import com.yashdotdev.api_gateway.rateLimiter.resolver.EndpointRateLimit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedirectEndpointMatcher implements EndpointMatcher {

    private final RateLimitProperties properties;

    @Override
    public Optional<EndpointRateLimit> match(
            ServerHttpRequest request
    ) {

        String path = request.getURI().getPath();

        if (!path.startsWith("/api/v1/r/")) {
            return Optional.empty();
        }

        String ip = request
                .getRemoteAddress()
                .getAddress()
                .getHostAddress();

        return Optional.of(
                new EndpointRateLimit(
                        RateLimitKeyGenerator.redirect(ip),
                        properties.getRedirect()
                )
        );
    }
}