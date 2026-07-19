package com.yashdotdev.api_gateway.security;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final List<String> PUBLIC_ENDPOINTS = List.of(

            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh",
            "/api/v1/auth/forgot-password",
            "/api/v1/auth/reset-password",

            "/actuator",
            "/actuator/**"

    );

    public final Predicate<ServerWebExchange> isSecured =
            exchange -> PUBLIC_ENDPOINTS.stream()
                    .noneMatch(uri ->
                            exchange.getRequest()
                                    .getURI()
                                    .getPath()
                                    .startsWith(uri));
}