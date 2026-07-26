package com.yashdotdev.api_gateway.filter;

import com.yashdotdev.api_gateway.config.RateLimitProperties;
import com.yashdotdev.api_gateway.rateLimiter.RateLimitKeyGenerator;
import com.yashdotdev.api_gateway.rateLimiter.RateLimitResult;
import com.yashdotdev.api_gateway.rateLimiter.RateLimitRule;
import com.yashdotdev.api_gateway.rateLimiter.RateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitingFilter
        implements GlobalFilter, Ordered {

    private final RateLimiterService rateLimiterService;
    private final RateLimitProperties properties;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        String ip = request
                .getRemoteAddress()
                .getAddress()
                .getHostAddress();

        RateLimitRule rule = null;
        String key;

        if (path.startsWith("/api/v1/auth/login")) {
            rule = properties.getLogin();
            key = RateLimitKeyGenerator.login(ip);
        }


        else if (path.startsWith("/api/v1/auth/register")) {
            rule = properties.getRegister();
            key = RateLimitKeyGenerator.register(ip);
        }

        else if (path.startsWith("/api/v1/r/")) {
            rule = properties.getRedirect();
            key = RateLimitKeyGenerator.redirect(ip);
        } else {
            key = null;
        }

        if (rule == null) {
            return chain.filter(exchange);
        }

        log.info("""
                Applying Rate Limit
                Path : {}
                Key  : {}

                """,
                path,
                key
        );

        return rateLimiterService
                .allowRequest(
                        key,
                        rule
                )
                .flatMap(result -> {
                    if (result.allowed()) {
                        return chain.filter(exchange);
                    }
                    log.warn("""
                            Rate Limit Exceeded

                            Key : {}
                            """,
                            key
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
                    return exchange
                            .getResponse()
                            .setComplete();
                });
    }

    @Override
    public int getOrder() {
        return -100;
    }
}