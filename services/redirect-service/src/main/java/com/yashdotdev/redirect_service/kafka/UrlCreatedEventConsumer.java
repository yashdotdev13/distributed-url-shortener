package com.yashdotdev.redirect_service.kafka;

import com.yashdotdev.common.constants.KafkaTopics;
import com.yashdotdev.common.events.UrlCreatedEvent;
import com.yashdotdev.redirect_service.bloom.BloomFilterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrlCreatedEventConsumer {

    private final BloomFilterService bloomFilterService;

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

        bloomFilterService.put(event.shortCode());
        log.info("""
                Bloom Filter Updated Successfully
                Short Code : {}
                """,
                event.shortCode()
        );
    }
}