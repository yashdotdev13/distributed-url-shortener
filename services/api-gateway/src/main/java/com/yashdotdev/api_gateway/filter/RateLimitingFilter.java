package com.yashdotdev.api_gateway.filter;

import com.yashdotdev.api_gateway.rateLimiter.RateLimitResult;
import com.yashdotdev.api_gateway.rateLimiter.RateLimiterService;
import com.yashdotdev.api_gateway.rateLimiter.resolver.EndpointRateLimit;
import com.yashdotdev.api_gateway.rateLimiter.resolver.EndpointRateLimitResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitingFilter
        implements GlobalFilter, Ordered {

    private final EndpointRateLimitResolver resolver;

    private final RateLimiterService rateLimiterService;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {

        return Mono.just(exchange.getRequest())
                .flatMap(request -> {

                    return resolver.resolve(request)

                            .map(endpointRateLimit ->

                                    rateLimiterService
                                            .allowRequest(
                                                    endpointRateLimit.getRedisKey(),
                                                    endpointRateLimit.getRule()
                                            )
                                            .flatMap(result ->
                                                    handleResult(
                                                            exchange,
                                                            chain,
                                                            result
                                                    )
                                            )

                            )

                            .orElseGet(() ->
                                    chain.filter(exchange)
                            );

                });

    }

    private Mono<Void> handleResult(
            ServerWebExchange exchange,
            GatewayFilterChain chain,
            RateLimitResult result
    ) {

        if (result.allowed()) {

            return chain.filter(exchange);
        }

        log.warn("""

                Rate Limit Exceeded

                Remaining Tokens : {}
                Retry After      : {} sec

                """,
                result.remainingTokens(),
                result.retryAfterSeconds()
        );

        exchange.getResponse()
                .setStatusCode(
                        HttpStatus.TOO_MANY_REQUESTS
                );

        exchange.getResponse()
                .getHeaders()
                .add(
                        "Retry-After",
                        String.valueOf(
                                result.retryAfterSeconds()
                        )
                );

        exchange.getResponse()
                .getHeaders()
                .add(
                        "X-RateLimit-Remaining",
                        String.valueOf(
                                result.remainingTokens()
                        )
                );

        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {

        return -100;
    }
}