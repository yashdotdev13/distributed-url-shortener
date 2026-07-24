package com.yashdotdev.common.events;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ClickEvents(

        String shortCode,

        String originalUrl,

        Long userId,

        Instant clickedAt
){}
