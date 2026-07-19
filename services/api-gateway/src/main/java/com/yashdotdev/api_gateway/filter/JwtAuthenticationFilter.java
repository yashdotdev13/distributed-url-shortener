package com.yashdotdev.api_gateway.filter;

import com.yashdotdev.api_gateway.constants.GatewayConstants;
import com.yashdotdev.api_gateway.exception.ErrorResponseWriter;
import com.yashdotdev.api_gateway.security.JwtService;
import com.yashdotdev.api_gateway.security.RouteValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;
    private final RouteValidator routeValidator;
    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        // Skip authentication for public endpoints
        if (!routeValidator.isSecured.test(exchange)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null ||
                !authHeader.startsWith(GatewayConstants.BEARER)) {

            log.warn("Missing Authorization Header");

            return errorResponseWriter.writeUnauthorized(
                    exchange,
                    "Missing Authorization Header"
            );
        }

        String token = authHeader.substring(GatewayConstants.BEARER.length());

        if (!jwtService.validateToken(token)) {

            log.warn("Invalid JWT Token");

            return errorResponseWriter.writeUnauthorized(
                    exchange,
                    "Invalid JWT Token"
            );
        }

        String username = jwtService.extractUsername(token);
        Long userId = jwtService.extractUserId(token);
        String roles = String.join(",", jwtService.extractRoles(token));

        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header(GatewayConstants.USERNAME, username)
                .header(GatewayConstants.USER_ID, String.valueOf(userId))
                .header(GatewayConstants.ROLES, roles)
                .build();

        log.info("""
                
                =====================================================
                JWT Authenticated
                =====================================================
                User ID  : {}
                Username : {}
                Roles    : {}
                =====================================================
                """,
                userId,
                username,
                roles
        );

        return chain.filter(
                exchange.mutate()
                        .request(request)
                        .build()
        );
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }
}