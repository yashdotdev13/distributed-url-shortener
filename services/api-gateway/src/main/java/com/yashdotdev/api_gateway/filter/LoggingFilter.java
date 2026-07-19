package com.yashdotdev.api_gateway.filter;

import com.yashdotdev.api_gateway.constants.GatewayConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        final String correlationId = getCorrelationId(exchange);

        final long startTime = System.currentTimeMillis();

        final String remoteIp = getRemoteIp(exchange);

        log.info("""
                
                Incoming Request
                Correlation ID : {}
                Method         : {}
                URI            : {}
                Remote IP      : {}
                """,
                correlationId,
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI().getPath(),
                remoteIp
        );

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    long executionTime = System.currentTimeMillis() - startTime;

                    log.info("""
                            
                            Outgoing Response
                            Correlation ID : {}
                            Status         : {}
                            Execution Time : {} ms
                            """,
                            correlationId,
                            exchange.getResponse().getStatusCode(),
                            executionTime
                    );
                }));
    }

    @Override
    public int getOrder() {
        // Runs immediately after CorrelationIdFilter
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    private String getCorrelationId(ServerWebExchange exchange) {

        String correlationId = exchange.getAttribute(GatewayConstants.CORRELATION_ID);

        if (correlationId == null || correlationId.isBlank()) {
            return "UNKNOWN";
        }

        return correlationId;
    }

    private String getRemoteIp(ServerWebExchange exchange) {

        if (exchange.getRequest().getRemoteAddress() != null
                && exchange.getRequest().getRemoteAddress().getAddress() != null) {

            return exchange.getRequest()
                    .getRemoteAddress()
                    .getAddress()
                    .getHostAddress();
        }

        return "UNKNOWN";
    }
}