package com.yashdotdev.analytic_service.kafka;


import com.yashdotdev.analytic_service.service.UrlOwnershipService;
import com.yashdotdev.common.constants.KafkaTopics;
import com.yashdotdev.common.events.UrlCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrlCreatedEventConsumer {

    private final UrlOwnershipService urlOwnershipService;

    @KafkaListener(
            topics = KafkaTopics.URL_CREATED_EVENTS,
            containerFactory = "urlCreatedKafkaListenerContainerFactory"
    )
    public void consume(UrlCreatedEvent event) {

        log.info("""

                URL CREATED EVENT RECEIVED

                Short Code : {}
                Owner Id   : {}

                """,
                event.shortCode(),
                event.userId()
        );

        urlOwnershipService.saveOwnership(event);
    }
}