package com.yashdotdev.analytic_service.dtos.dashboard;

import lombok.Builder;

@Builder
public record  BrowserAnalyticsResponse (

        String browser,
        Long clicks
){}
