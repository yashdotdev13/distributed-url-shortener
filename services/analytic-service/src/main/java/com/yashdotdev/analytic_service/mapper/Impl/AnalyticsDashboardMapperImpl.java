package com.yashdotdev.analytic_service.mapper.Impl;


import com.yashdotdev.analytic_service.dtos.dashboard.BrowserAnalyticsResponse;
import com.yashdotdev.analytic_service.dtos.dashboard.DeviceAnalyticsResponse;
import com.yashdotdev.analytic_service.dtos.dashboard.OperatingSystemAnalyticsResponse;
import com.yashdotdev.analytic_service.dtos.dashboard.SummaryResponse;
import com.yashdotdev.analytic_service.entity.aggregate.BrowserAnalytics;
import com.yashdotdev.analytic_service.entity.aggregate.DeviceAnalytics;
import com.yashdotdev.analytic_service.entity.aggregate.OperatingSystemAnalytics;
import com.yashdotdev.analytic_service.entity.raw.UrlAnalytics;
import com.yashdotdev.analytic_service.mapper.AnalyticsDashboardMapper;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsDashboardMapperImpl
        implements AnalyticsDashboardMapper {

    @Override
    public SummaryResponse toSummary(
            UrlAnalytics analytics
    ) {

        return SummaryResponse.builder()
                .totalClicks(analytics.getTotalClicks())
                .lastClickedAt(analytics.getLastClickedAt())
                .build();
    }

    @Override
    public BrowserAnalyticsResponse toResponse(
            BrowserAnalytics analytics
    ) {

        return BrowserAnalyticsResponse.builder()
                .browser(analytics.getBrowser().name())
                .clicks(analytics.getClicks())
                .build();
    }

    @Override
    public DeviceAnalyticsResponse toResponse(
            DeviceAnalytics analytics
    ) {

        return DeviceAnalyticsResponse.builder()
                .device(analytics.getDeviceType().name())
                .clicks(analytics.getClicks())
                .build();
    }

    @Override
    public OperatingSystemAnalyticsResponse toResponse(
            OperatingSystemAnalytics analytics
    ) {

        return OperatingSystemAnalyticsResponse.builder()
                .operatingSystem(
                        analytics.getOperatingSystem().name()
                )
                .clicks(analytics.getClicks())
                .build();
    }
}