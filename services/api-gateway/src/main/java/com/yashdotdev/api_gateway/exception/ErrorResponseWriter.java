package com.yashdotdev.api_gateway.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashdotdev.api_gateway.constants.GatewayConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ErrorResponseWriter {


    private final ObjectMapper objectMapper;

    public Mono<Void> writeUnauthorized(ServerWebExchange exchange,
                                        String message) {

        String correlationId =
                exchange.getAttribute(GatewayConstants.CORRELATION_ID);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(message)
                .path(exchange.getRequest().getPath().value())
                .correlationId(correlationId)
                .build();

        ServerHttpResponse httpResponse = exchange.getResponse();

        httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        httpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {

            byte[] bytes = objectMapper.writeValueAsBytes(response);

            return httpResponse.writeWith(
                    Mono.just(
                            httpResponse.bufferFactory().wrap(bytes)
                    )
            );

        } catch (Exception e) {

            byte[] bytes = """
                    {
                        "status":401,
                        "message":"Unauthorized"
                    }
                    """.getBytes(StandardCharsets.UTF_8);

            return httpResponse.writeWith(
                    Mono.just(
                            httpResponse.bufferFactory().wrap(bytes)
                    )
            );
        }
    }
}
