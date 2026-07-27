package com.yashdotdev.api_gateway.rateLimiter.matcher;

import com.yashdotdev.api_gateway.rateLimiter.resolver.EndpointRateLimit;
import com.yashdotdev.api_gateway.rateLimiter.resolver.EndpointRateLimitResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DefaultEndpointRateLimitResolver
        implements EndpointRateLimitResolver {

    private final List<EndpointMatcher> endpointMatchers;

    @Override
    public Optional<EndpointRateLimit> resolve(
            ServerHttpRequest request
    ) {

        return endpointMatchers.stream()
                .map(matcher -> matcher.match(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}
