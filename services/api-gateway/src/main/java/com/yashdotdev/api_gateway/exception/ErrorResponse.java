package com.yashdotdev.api_gateway.exception;

import lombok.Builder;

import java.time.Instant;


@Builder
public record ErrorResponse (
        Instant timestamp,

        int status,

        String error,

        String message,

        String path,

        String correlationId
){}