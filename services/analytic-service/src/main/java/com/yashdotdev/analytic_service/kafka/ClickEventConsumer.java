package com.yashdotdev.analytic_service.kafka;


import com.yashdotdev.common.constants.KafkaTopics;
import com.yashdotdev.common.events.ClickEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClickEventConsumer {

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
    }
}
