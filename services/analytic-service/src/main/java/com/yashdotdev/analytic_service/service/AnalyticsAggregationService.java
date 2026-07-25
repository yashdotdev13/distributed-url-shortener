package com.yashdotdev.analytic_service.service;

import com.yashdotdev.analytic_service.enums.BrowserType;
import com.yashdotdev.analytic_service.enums.DeviceType;
import com.yashdotdev.analytic_service.enums.OperatingSystem;

import java.time.Instant;

public interface AnalyticsAggregationService {

    void aggregate(
            String shortCode,

            BrowserType browser,

            DeviceType device,
            OperatingSystem system,

            Instant clickedAt

    );
}
