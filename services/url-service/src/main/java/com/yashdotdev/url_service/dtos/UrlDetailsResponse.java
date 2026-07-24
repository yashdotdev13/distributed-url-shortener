
package com.yashdotdev.url_service.dtos;

import com.yashdotdev.url_service.enums.UrlStatus;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UrlDetailsResponse(

        Long id,

        String originalUrl,

        String shortCode,

        String shortUrl,

        Long userId,

        UrlStatus status,

        Long clickCount,

        Instant createdAt,

        Instant updatedAt,

        Instant expiresAt,

        Instant lastAccessedAt

) {
}