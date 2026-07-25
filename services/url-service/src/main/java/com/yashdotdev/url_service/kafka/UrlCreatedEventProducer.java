package com.yashdotdev.url_service.kafka;

import com.yashdotdev.common.constants.KafkaTopics;
import com.yashdotdev.common.events.UrlCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlCreatedEventProducer {

    private final KafkaTemplate<String, UrlCreatedEvent> kafkaTemplate;

    public void publish(UrlCreatedEvent event) {

        kafkaTemplate.send(
                KafkaTopics.URL_CREATED_EVENTS,
                event.shortCode(),
                event
        ).whenComplete((result, ex) -> {

            if (ex != null) {

                log.error("""
                        Failed to publish UrlCreatedEvent
                        Short Code : {}
                        """,
                        event.shortCode(),
                        ex
                );

                return;
            }

            log.info("""
                    UrlCreatedEvent Published Successfully
                    Topic     : {}
                    Partition : {}
                    Offset    : {}
                    """,
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
            );
        });
    }
}