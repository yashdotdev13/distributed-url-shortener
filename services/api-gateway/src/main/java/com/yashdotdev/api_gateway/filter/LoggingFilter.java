package com.yashdotdev.api_gateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String requestId = UUID.randomUUID().toString();
        exchange.getAttributes().put("requestId", requestId);
        Long startTime = System.currentTimeMillis();

        log.info("""
                        
                  
                        Incoming Request
                        
                        Request ID : {}
                        Method     : {}
                        URI        : {}
                        Remote IP  : {}
                        
                        """,
                requestId,
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI().getPath(),
                exchange.getRequest().getRemoteAddress()
        );

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    long executionTime = System.currentTimeMillis() - startTime;

                    log.info("""
                            
                            Outgoing Response
                            Request ID      : {}
                            Status          : {}
                            Execution Time  : {} ms
                            
                            """,
                            requestId,
                            exchange.getResponse().getStatusCode(),
                            executionTime
                    );

                }));

    }

    @Override
    public int getOrder() {
        return -1;
    }
}
