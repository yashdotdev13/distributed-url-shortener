package com.yashdotdev.analytic_service.config;

import com.yashdotdev.common.events.ClickEvents;
import com.yashdotdev.common.events.UrlCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    private Map<String, Object> consumerProperties() {

        Map<String, Object> props = new HashMap<>();

        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                groupId
        );

        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class
        );

        props.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest"
        );

        return props;
    }

    @Bean
    public ConsumerFactory<String, ClickEvents> clickEventConsumerFactory() {

        JsonDeserializer<ClickEvents> deserializer =
                new JsonDeserializer<>(ClickEvents.class);

        deserializer.addTrustedPackages(
                "com.yashdotdev.common.events"
        );

        return new DefaultKafkaConsumerFactory<>(
                consumerProperties(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConsumerFactory<String, UrlCreatedEvent> urlCreatedConsumerFactory() {

        JsonDeserializer<UrlCreatedEvent> deserializer =
                new JsonDeserializer<>(UrlCreatedEvent.class);

        deserializer.addTrustedPackages(
                "com.yashdotdev.common.events"
        );

        return new DefaultKafkaConsumerFactory<>(
                consumerProperties(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClickEvents>
    kafkaListenerContainerFactory(
            DefaultErrorHandler kafkaErrorHandler
    ) {

        ConcurrentKafkaListenerContainerFactory<String, ClickEvents> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                clickEventConsumerFactory()
        );

        factory.setCommonErrorHandler(
                kafkaErrorHandler
        );

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UrlCreatedEvent>
    urlCreatedKafkaListenerContainerFactory(
            DefaultErrorHandler kafkaErrorHandler
    ) {

        ConcurrentKafkaListenerContainerFactory<String, UrlCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                urlCreatedConsumerFactory()
        );

        factory.setCommonErrorHandler(
                kafkaErrorHandler
        );

        return factory;
    }

}