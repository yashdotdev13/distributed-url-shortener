package com.yashdotdev.redirect_service.producer;

import com.yashdotdev.common.constants.KafkaTopics;
import com.yashdotdev.common.events.ClickEvents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClickEventProducer {

    private final KafkaTemplate<String, ClickEvents> kafkaTemplate;

    public void publish(ClickEvents event) {

        kafkaTemplate.send(
                KafkaTopics.CLICK_EVENTS,
                event.shortCode(),
                event
        ).whenComplete((result, ex) -> {

            if (ex != null) {

                log.error("""
                                
                                Failed to publish Click Event
                                
                                Short Code : {}
                                
                                """,
                        event.shortCode(),
                        ex
                );

                return;
            }

            log.info("""
                            
                            Click Event Published Successfully
                            
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