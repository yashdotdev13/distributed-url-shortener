package com.yashdotdev.redirect_service.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserContextFilter implements WebFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        String userId =
                request.getHeaders().getFirst(SecurityConstants.USER_ID);

        String username =
                request.getHeaders().getFirst(SecurityConstants.USERNAME);

        if (userId == null || username == null) {
            log.warn("""
                    
                    Missing authenticated user headers.
                    
                    URI : {}
                    
                    """,
                    request.getURI()
            );
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}