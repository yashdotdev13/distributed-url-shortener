package com.yashdotdev.analytic_service.kafka;

import com.yashdotdev.common.constants.KafkaTopics;
import com.yashdotdev.common.events.ClickEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClickEventDeadLetterConsumer {

    @KafkaListener(
            topics = KafkaTopics.CLICK_EVENTS_DLT,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ClickEvents event) {

        log.error("""
                CLICK EVENT SENT TO DEAD LETTER QUEUE

                Short Code : {}
                User Id    : {}
                URL        : {}
                Clicked At : {}

                """,
                event.shortCode(),
                event.userId(),
                event.originalUrl(),
                event.clickedAt()
        );

        /*
         * Future Improvements
         *
         * 1. Persist into failed_events table
         * 2. Send Alert
         * 3. Slack Notification
         * 4. Replay Endpoint
         */
    }
}