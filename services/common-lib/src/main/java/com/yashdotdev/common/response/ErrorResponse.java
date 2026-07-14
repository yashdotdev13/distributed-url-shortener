package com.yashdotdev.common.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private Boolean success;

    private String errorCode;

    private String message;

    private String path;

    private int status;

    private String correlationId;

    @Builder.Default
    private Instant timestamp = Instant.now();
}
