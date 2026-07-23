package com.yashdotdev.url_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record CreateShortUrlRequest(

        @NotBlank(message = "Original URL is required")

        @URL(message = "Please provide a valid URL")

        @Size(max = 2048)

        String originalUrl

) {}