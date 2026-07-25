package com.yashdotdev.analytic_service.dtos.dashboard;


import lombok.Builder;
import java.util.List;

@Builder
public record AnalyticsDashboardResponse(

        String shortCode,
        SummaryResponse summary,
        List<BrowserAnalyticsResponse> browsers,
        List<DeviceAnalyticsResponse> devices,
        List<OperatingSystemAnalyticsResponse> operatingSystems

) {
}