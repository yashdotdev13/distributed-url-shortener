package com.yashdotdev.analytic_service.mapper;

import com.yashdotdev.analytic_service.dtos.dashboard.BrowserAnalyticsResponse;
import com.yashdotdev.analytic_service.dtos.dashboard.DeviceAnalyticsResponse;
import com.yashdotdev.analytic_service.dtos.dashboard.OperatingSystemAnalyticsResponse;
import com.yashdotdev.analytic_service.dtos.dashboard.SummaryResponse;
import com.yashdotdev.analytic_service.entity.aggregate.BrowserAnalytics;
import com.yashdotdev.analytic_service.entity.aggregate.DeviceAnalytics;
import com.yashdotdev.analytic_service.entity.aggregate.OperatingSystemAnalytics;
import com.yashdotdev.analytic_service.entity.raw.UrlAnalytics;

public interface AnalyticsDashboardMapper {

    SummaryResponse toSummary(
            UrlAnalytics analytics
    );

    BrowserAnalyticsResponse toResponse(
            BrowserAnalytics analytics
    );

    DeviceAnalyticsResponse toResponse(
            DeviceAnalytics analytics
    );

    OperatingSystemAnalyticsResponse toResponse(
            OperatingSystemAnalytics analytics
    );

}