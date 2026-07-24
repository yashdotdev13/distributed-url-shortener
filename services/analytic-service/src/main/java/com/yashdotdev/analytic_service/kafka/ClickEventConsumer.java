package com.yashdotdev.analytic_service.kafka;


import com.yashdotdev.analytic_service.service.AnalyticsService;
import com.yashdotdev.common.constants.KafkaTopics;
import com.yashdotdev.common.events.ClickEvents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClickEventConsumer {

    private final AnalyticsService analyticsService;

    @KafkaListener(
            topics = KafkaTopics.CLICK_EVENTS,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ClickEvents event) {

        log.info("""
                CLICK EVENT RECEIVED

                Short Code : {}
                Original URL : {}
                User Id    : {}
                Clicked At : {}

                """,
                event.shortCode(),
                event.originalUrl(),
                event.userId(),
                event.clickedAt()
        );

        analyticsService.saveClickEvent(event);
    }
}
