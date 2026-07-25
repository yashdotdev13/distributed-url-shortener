package com.yashdotdev.url_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;

public record CreateShortUrlRequest(

        @NotBlank(message = "Original URL is required")
        @URL(message = "Please provide a valid URL")
        @Size(max = 2048)
        String originalUrl,

                 @Size(
                 min = 3,
                 max = 30,
                 message = "Alias must be between 3 and 30 characters."
         )
                 String customAlias,

                 Instant expiresAt


) {}