package com.yashdotdev.url_service.mapper;


import com.yashdotdev.url_service.dtos.CreateShortUrlRequest;
import com.yashdotdev.url_service.dtos.ShortUrlResponse;
import com.yashdotdev.url_service.dtos.UrlDetailsResponse;
import com.yashdotdev.url_service.entity.Url;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UrlMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "originalUrl", source = "request.originalUrl")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "shortCode", source = "shortCode")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "clickCount", constant = "0")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    @Mapping(target = "lastAccessedAt", ignore = true)
    Url toEntity(
            CreateShortUrlRequest request,
            Long userId,
            String shortCode
    );

    @Mapping(
            target = "shortUrl",
            expression = "java(buildShortUrl(url.getShortCode()))"
    )
    ShortUrlResponse toShortUrlResponse(Url url);

    @Mapping(
            target = "shortUrl",
            expression = "java(buildShortUrl(url.getShortCode()))"
    )
    UrlDetailsResponse toUrlDetailsResponse(Url url);

    default String buildShortUrl(String shortCode) {
        return "http://localhost:8081/" + shortCode;
    }
}