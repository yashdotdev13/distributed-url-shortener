package com.yashdotdev.api_gateway.service;

import com.yashdotdev.api_gateway.constants.RateLimitConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final ReactiveStringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> rateLimitScript;

    public Mono<Boolean> isAllowed(String key) {

        return redisTemplate.execute(
                        rateLimitScript,
                        Collections.singletonList(key),
                        String.valueOf(RateLimitConstants.WINDOW_DURATION_SECONDS),
                        String.valueOf(RateLimitConstants.MAX_REQUESTS)
                )
                .next()
                .map(result -> result == 1L)
                .defaultIfEmpty(false);
    }
}