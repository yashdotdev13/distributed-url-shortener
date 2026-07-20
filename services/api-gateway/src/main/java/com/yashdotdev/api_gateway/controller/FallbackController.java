package com.yashdotdev.api_gateway.controller;

import com.yashdotdev.api_gateway.exception.ErrorResponseWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class FallbackController {

    private final ErrorResponseWriter errorResponseWriter;

    @RequestMapping("/fallback/auth")
    public Mono<Void> authServiceFallback(ServerWebExchange exchange) {

        return errorResponseWriter.writeServiceUnavailable(
                exchange,
                "Auth Service is temporarily unavailable. Please try again later."
        );
    }
}
