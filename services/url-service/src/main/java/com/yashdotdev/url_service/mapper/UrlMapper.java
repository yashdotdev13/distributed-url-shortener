package com.yashdotdev.url_service.mapper;

import com.yashdotdev.url_service.dtos.CreateShortUrlRequest;
import com.yashdotdev.url_service.dtos.ShortUrlResponse;
import com.yashdotdev.url_service.dtos.UrlDetailsResponse;
import com.yashdotdev.url_service.dtos.UrlSummaryResponse;
import com.yashdotdev.url_service.entity.Url;
import com.yashdotdev.url_service.enums.UrlStatus;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {

    private static final String BASE_URL = "http://localhost:8081/";

    public Url toEntity(
            CreateShortUrlRequest request,
            Long userId,
            String shortCode
    ) {

        return Url.builder()
                .originalUrl(request.originalUrl())
                .shortCode(shortCode)
                .userId(userId)
                .status(UrlStatus.ACTIVE)
                .clickCount(0L)
                .build();
    }

    public ShortUrlResponse toShortUrlResponse(Url url) {

        return ShortUrlResponse.builder()
                .id(url.getId())
                .shortCode(url.getShortCode())
                .shortUrl(BASE_URL + url.getShortCode())
                .build();
    }

    public UrlDetailsResponse toUrlDetailsResponse(Url url) {

        return UrlDetailsResponse.builder()
                .id(url.getId())
                .originalUrl(url.getOriginalUrl())
                .shortCode(url.getShortCode())
                .shortUrl(BASE_URL + url.getShortCode())
                .userId(url.getUserId())
                .status(url.getStatus())
                .clickCount(url.getClickCount())
                .createdAt(url.getCreatedAt())
                .updatedAt(url.getUpdatedAt())
                .expiresAt(url.getExpiresAt())
                .lastAccessedAt(url.getLastAccessedAt())
                .build();
    }

    public UrlSummaryResponse toUrlSummaryResponse(Url url) {

        return UrlSummaryResponse.builder()
                .id(url.getId())
                .originalUrl(url.getOriginalUrl())
                .shortCode(url.getShortCode())
                .shortUrl(BASE_URL + url.getShortCode())
                .status(url.getStatus())
                .clickCount(url.getClickCount())
                .createdAt(url.getCreatedAt())
                .build();
    }
}