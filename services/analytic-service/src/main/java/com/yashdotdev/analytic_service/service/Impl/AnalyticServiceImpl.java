package com.yashdotdev.analytic_service.service.Impl;

import com.yashdotdev.analytic_service.dtos.UserAgentMetadata;
import com.yashdotdev.analytic_service.entity.raw.ClickEvent;
import com.yashdotdev.analytic_service.mapper.ClickEventMapper;
import com.yashdotdev.analytic_service.repository.ClickEventRepository;
import com.yashdotdev.analytic_service.service.AnalyticsAggregationService;
import com.yashdotdev.analytic_service.service.AnalyticsService;
import com.yashdotdev.analytic_service.service.parser.UserAgentParserService;
import com.yashdotdev.common.events.ClickEvents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalyticServiceImpl implements AnalyticsService {

    private final ClickEventRepository clickEventRepository;
    private final ClickEventMapper clickEventMapper;
    private final UserAgentParserService userAgentParserService;
    private final AnalyticsAggregationService analyticsAggregationService;

    @Override
    public void saveClickEvent(ClickEvents event) {

        log.info("""
                Persisting Click Event
                Short Code : {}
                User Id    : {}
                """,
                event.shortCode(),
                event.userId()
        );

        UserAgentMetadata metadata =
                userAgentParserService.parse(
                        event.userAgent()
                );

        log.info("""
                User-Agent Parsed
                Browser : {}
                OS       : {}
                Device   : {}
                """,
                metadata.browser(),
                metadata.operatingSystem(),
                metadata.deviceType()
        );

        ClickEvent clickEvent = clickEventMapper.toEntity(event);

        clickEvent.setBrowser(metadata.browser());
        clickEvent.setOperatingSystem(metadata.operatingSystem());
        clickEvent.setDeviceType(metadata.deviceType());


        clickEventRepository.save(clickEvent);

        analyticsAggregationService.aggregate(

                event.shortCode(),
                metadata.browser(),
                metadata.deviceType(),
                metadata.operatingSystem(),
                event.clickedAt()

        );

        log.info("""
                Click Event Saved Successfully
                Event Id : {}
                """,
                clickEvent.getId()
        );
    }
}