package com.yashdotdev.url_service.dtos;


import lombok.Builder;

@Builder
public record ShortUrlResponse(

        Long id,

        String shortCode,

        String shortUrl

) {}