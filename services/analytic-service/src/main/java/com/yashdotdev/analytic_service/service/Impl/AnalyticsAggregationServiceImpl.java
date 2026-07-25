package com.yashdotdev.analytic_service.service.Impl;

import com.yashdotdev.analytic_service.enums.BrowserType;
import com.yashdotdev.analytic_service.enums.DeviceType;
import com.yashdotdev.analytic_service.enums.OperatingSystem;
import com.yashdotdev.analytic_service.repository.BrowserAnalyticsRepository;
import com.yashdotdev.analytic_service.repository.DeviceAnalyticsRepository;
import com.yashdotdev.analytic_service.repository.OperatingSystemAnalyticsRepository;
import com.yashdotdev.analytic_service.repository.UrlAnalyticsRepository;
import com.yashdotdev.analytic_service.service.AnalyticsAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


@Service
@RequiredArgsConstructor
public class AnalyticsAggregationServiceImpl implements AnalyticsAggregationService {


    private final UrlAnalyticsRepository urlAnalyticsRepository;
    private final BrowserAnalyticsRepository browserAnalyticsRepository;
    private final DeviceAnalyticsRepository deviceAnalyticsRepository;
    private final OperatingSystemAnalyticsRepository operatingSystemAnalyticsRepository;


    @Override
    @Transactional
    public void aggregate(

            String shortCode,
            BrowserType browser,
            DeviceType device,
            OperatingSystem operatingSystem,
            Instant clickedAt

    ) {

        urlAnalyticsRepository.upsertAnalytics(
                shortCode,
                clickedAt
        );
        browserAnalyticsRepository.upsert(
                shortCode,
                browser.name()
        );

        deviceAnalyticsRepository.upsert(
                shortCode,
                device.name()
        );

        operatingSystemAnalyticsRepository.upsert(
                shortCode,
                operatingSystem.name()
        );
    }
}
