package com.yashdotdev.analytic_service.service.Impl;


import com.yashdotdev.analytic_service.entity.ClickEvent;
import com.yashdotdev.analytic_service.mapper.ClickEventMapper;
import com.yashdotdev.analytic_service.repository.ClickEventRepository;
import com.yashdotdev.analytic_service.service.AnalyticsService;
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

        ClickEvent clickEvent = clickEventMapper.toEntity(event);
        clickEventRepository.save(clickEvent);

        log.info("""
                Click Event Saved Successfully
                Event Id : {}
                """,
                clickEvent.getId()
        );
    }
}
