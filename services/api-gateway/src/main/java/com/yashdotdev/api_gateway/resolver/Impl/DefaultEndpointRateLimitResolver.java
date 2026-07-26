package com.yashdotdev.api_gateway.resolver.Impl;


import com.yashdotdev.api_gateway.config.RateLimitProperties;
import com.yashdotdev.api_gateway.rateLimiter.RateLimitKeyGenerator;
import com.yashdotdev.api_gateway.resolver.EndpointRateLimit;
import com.yashdotdev.api_gateway.resolver.EndpointRateLimitResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DefaultEndpointRateLimitResolver
        implements EndpointRateLimitResolver {

    private final RateLimitProperties properties;

    @Override
    public Optional<EndpointRateLimit> resolve(
            ServerHttpRequest request
    ) {

        String path = request.getURI().getPath();

        String ip = request
                .getRemoteAddress()
                .getAddress()
                .getHostAddress();

        if (path.startsWith("/api/v1/auth/login")) {

            return Optional.of(
                    new EndpointRateLimit(
                            RateLimitKeyGenerator.login(ip),
                            properties.getLogin()
                    )
            );
        }

        if (path.startsWith("/api/v1/auth/register")) {

            return Optional.of(
                    new EndpointRateLimit(
                            RateLimitKeyGenerator.register(ip),
                            properties.getRegister()
                    )
            );
        }

        if (path.startsWith("/api/v1/r/")) {

            return Optional.of(
                    new EndpointRateLimit(
                            RateLimitKeyGenerator.redirect(ip),
                            properties.getRedirect()
                    )
            );
        }
        return Optional.empty();
    }
}