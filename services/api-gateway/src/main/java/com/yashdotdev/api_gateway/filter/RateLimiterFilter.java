package com.yashdotdev.api_gateway.filter;

import com.yashdotdev.api_gateway.constants.RateLimitConstants;
import com.yashdotdev.api_gateway.exception.ErrorResponseWriter;
import com.yashdotdev.api_gateway.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimiterFilter implements GlobalFilter, Ordered {

    private final RateLimiterService rateLimiterService;
    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String clientIp = getClientIp(exchange);

        String redisKey = RateLimitConstants.RATE_LIMIT_PREFIX +
                "ip:" +
                clientIp;

        return rateLimiterService.isAllowed(redisKey)
                .flatMap(allowed -> {

                    if (!allowed) {

                        log.warn("""
                                
                                
                                Rate Limit Exceeded
                                
                                Client IP : {}
                                Redis Key : {}
                                
                                """,
                                clientIp,
                                redisKey
                        );

                        return errorResponseWriter.writeTooManyRequests(
                                exchange,
                                RateLimitConstants.TOO_MANY_REQUESTS
                        );
                    }

                    return chain.filter(exchange);
                });
    }

    private String getClientIp(ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();

        String forwardedFor = request.getHeaders().getFirst("X-Forwarded-For");

        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddress() != null
                ? request.getRemoteAddress().getAddress().getHostAddress()
                : "unknown";
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }
}