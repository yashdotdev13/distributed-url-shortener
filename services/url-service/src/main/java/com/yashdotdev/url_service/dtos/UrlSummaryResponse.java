package com.yashdotdev.url_service.dtos;


import com.yashdotdev.url_service.enums.UrlStatus;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UrlSummaryResponse (

        Long id,

        String originalUrl,

        String shortCode,

        String shortUrl,

        UrlStatus status,

        Long clickCount,

        Instant createdAt
){}
