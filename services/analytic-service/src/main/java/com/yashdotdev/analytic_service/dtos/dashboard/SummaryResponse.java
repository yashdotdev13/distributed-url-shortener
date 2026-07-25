package com.yashdotdev.analytic_service.dtos.dashboard;


import lombok.Builder;

import java.time.Instant;

@Builder
public record SummaryResponse (

        Long totalClicks,
        Instant lastClickedAt
){}
