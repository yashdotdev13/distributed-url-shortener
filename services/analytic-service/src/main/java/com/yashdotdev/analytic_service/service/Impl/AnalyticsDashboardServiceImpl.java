package com.yashdotdev.analytic_service.service.Impl;



import com.yashdotdev.analytic_service.dtos.dashboard.*;
import com.yashdotdev.analytic_service.entity.aggregate.BrowserAnalytics;
import com.yashdotdev.analytic_service.entity.aggregate.DeviceAnalytics;
import com.yashdotdev.analytic_service.entity.aggregate.OperatingSystemAnalytics;
import com.yashdotdev.analytic_service.entity.ownership.UrlOwnership;
import com.yashdotdev.analytic_service.entity.raw.UrlAnalytics;
import com.yashdotdev.analytic_service.exception.AnalyticsAccessDeniedException;
import com.yashdotdev.analytic_service.exception.ShortUrlOwnershipNotFoundException;
import com.yashdotdev.analytic_service.mapper.AnalyticsDashboardMapper;
import com.yashdotdev.analytic_service.repository.*;
import com.yashdotdev.analytic_service.security.AuthenticatedUser;
import com.yashdotdev.analytic_service.service.AnalyticsDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticsDashboardServiceImpl
        implements AnalyticsDashboardService {

    private final UrlAnalyticsRepository urlAnalyticsRepository;
    private final BrowserAnalyticsRepository browserAnalyticsRepository;
    private final DeviceAnalyticsRepository deviceAnalyticsRepository;
    private final OperatingSystemAnalyticsRepository
            operatingSystemAnalyticsRepository;
    private final UrlOwnershipRepository urlOwnershipRepository;

    private final AnalyticsDashboardMapper dashboardMapper;

    @Override
    public AnalyticsDashboardResponse getDashboard(
            String shortCode,
            AuthenticatedUser currentUser
    ) {


        log.info("""

            Building Analytics Dashboard

            Short Code : {}
            User Id    : {}

            """,
                shortCode,
                currentUser.userId()
        );

        UrlOwnership ownership =
                urlOwnershipRepository
                        .findById(shortCode)
                        .orElseThrow(() ->
                                new ShortUrlOwnershipNotFoundException(shortCode)
                        );

        if (!ownership.getOwnerId().equals(currentUser.userId())) {

            throw new AnalyticsAccessDeniedException(
                    shortCode
            );
        }

        UrlAnalytics summary = urlAnalyticsRepository
                .findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortUrlOwnershipNotFoundException(shortCode)
                );


        List<BrowserAnalytics> browsers =
                browserAnalyticsRepository
                        .findByShortCodeOrderByClicksDesc(shortCode);


        List<DeviceAnalytics> devices =
                deviceAnalyticsRepository
                        .findByShortCodeOrderByClicksDesc(shortCode);


        List<OperatingSystemAnalytics> operatingSystems =
                operatingSystemAnalyticsRepository
                        .findByShortCodeOrderByClicksDesc(shortCode);


        SummaryResponse summaryResponse =
                dashboardMapper.toSummary(summary);

        List<BrowserAnalyticsResponse> browserResponses =
                browsers.stream()
                        .map(dashboardMapper::toResponse)
                        .toList();

        List<DeviceAnalyticsResponse> deviceResponses =
                devices.stream()
                        .map(dashboardMapper::toResponse)
                        .toList();

        List<OperatingSystemAnalyticsResponse> osResponses =
                operatingSystems.stream()
                        .map(dashboardMapper::toResponse)
                        .toList();


        return AnalyticsDashboardResponse.builder()
                .shortCode(shortCode)
                .summary(summaryResponse)
                .browsers(browserResponses)
                .devices(deviceResponses)
                .operatingSystems(osResponses)
                .build();
    }
}