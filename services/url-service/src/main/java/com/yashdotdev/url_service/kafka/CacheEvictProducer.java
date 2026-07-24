package com.yashdotdev.url_service.kafka;


import com.yashdotdev.common.constants.KafkaTopics;
import com.yashdotdev.common.events.CacheEvictEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheEvictProducer {

    private final KafkaTemplate<String, CacheEvictEvent> kafkaTemplate;

    public void publish(String shortCode) {

        CacheEvictEvent event = CacheEvictEvent.builder()
                .shortCode(shortCode)
                .build();

        kafkaTemplate.send(
                KafkaTopics.CACHE_EVICT,
                shortCode,
                event
        ).whenComplete((result, ex) -> {

            if (ex != null) {
                log.error("""
                        Failed to Publish Cache Eviction Event
                        Short Code : {}
                        """,
                        shortCode,
                        ex
                );
                return;
            }
            log.info("""

                    Cache Eviction Event Published
                    Topic      : {}
                    Partition  : {}
                    Offset     : {}
                    Short Code : {}

                    """,
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset(),
                    shortCode
            );
        });
    }
}