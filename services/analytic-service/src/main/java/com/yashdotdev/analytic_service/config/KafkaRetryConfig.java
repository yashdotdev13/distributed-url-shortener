package com.yashdotdev.analytic_service.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@RequiredArgsConstructor
public class KafkaRetryConfig {

    private static final long BACKOFF_INTERVAL = 5000L;
    private static final long MAX_RETRIES = 3L;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer() {
        return new DeadLetterPublishingRecoverer(

                kafkaTemplate,

                (
                        ConsumerRecord<?, ?> record,
                        Exception exception
                ) -> new TopicPartition(

                        record.topic() + "-dlt",

                        record.partition()
                )
        );
    }

    @Bean
    public DefaultErrorHandler kafkaErrorHandler(
            DeadLetterPublishingRecoverer recoverer
    ) {

        DefaultErrorHandler errorHandler =
                new DefaultErrorHandler(

                        recoverer,
                        new FixedBackOff(

                                BACKOFF_INTERVAL,
                                MAX_RETRIES
                        )
                );

        errorHandler.addNotRetryableExceptions(
                IllegalArgumentException.class,
                NullPointerException.class
        );
        return errorHandler;
    }

}