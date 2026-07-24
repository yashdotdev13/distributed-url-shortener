package com.yashdotdev.redirect_service.kafka;


import com.yashdotdev.common.constants.KafkaTopics;
import com.yashdotdev.common.events.CacheEvictEvent;
import com.yashdotdev.redirect_service.cache.UrlCacheService;
import com.yashdotdev.redirect_service.service.CacheEvictionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheEvictConsumer {

    private final UrlCacheService urlCacheService;
    private final CacheEvictionService cacheEvictionService;

    @KafkaListener(
            topics = KafkaTopics.CACHE_EVICT,
            groupId = "redirect-cache-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(CacheEvictEvent event) {

        log.info("""

                Cache Eviction Event Received
                Short Code : {}
                """,
                event.shortCode()
        );

        cacheEvictionService.evict(event.shortCode());

        log.info("""
                Redis Cache Evicted Successfully
                Short Code : {}
                """,
                event.shortCode()
        );
    }
}