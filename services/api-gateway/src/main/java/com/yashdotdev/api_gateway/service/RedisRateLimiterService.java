package com.yashdotdev.api_gateway.service;

import com.yashdotdev.api_gateway.rateLimiter.RateLimitResult;
import com.yashdotdev.api_gateway.rateLimiter.RateLimitRule;
import com.yashdotdev.api_gateway.rateLimiter.RateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisRateLimiterService implements RateLimiterService {

    private final ReactiveStringRedisTemplate redisTemplate;

    private final DefaultRedisScript<List> tokenBucketScript;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<RateLimitResult> allowRequest(
            String key,
            RateLimitRule rule
    ) {

        log.info("""

                Executing Distributed Token Bucket

                Key : {}

                Capacity        : {}
                Refill Tokens   : {}
                Refill Duration : {} sec

                """,
                key,
                rule.getCapacity(),
                rule.getRefillTokens(),
                rule.getRefillDurationSeconds()
        );

        return redisTemplate.execute(
                        tokenBucketScript,
                        List.of(key),

                        String.valueOf(rule.getCapacity()),
                        String.valueOf(rule.getRefillTokens()),
                        String.valueOf(rule.getRefillDurationSeconds()),
                        String.valueOf(Instant.now().getEpochSecond())
                )
                .next()
                .map(result -> {

                    long allowed =
                            ((Number) result.get(0)).longValue();

                    long remainingTokens =
                            ((Number) result.get(1)).longValue();

                    long retryAfter =
                            ((Number) result.get(2)).longValue();

                    log.info("""

                            Rate Limiter Result

                            Allowed         : {}
                            RemainingTokens : {}
                            Retry After     : {} sec

                            """,
                            allowed == 1,
                            remainingTokens,
                            retryAfter
                    );

                    return RateLimitResult.builder()
                            .allowed(allowed == 1)
                            .remainingTokens(remainingTokens)
                            .retryAfterSeconds(retryAfter)
                            .build();
                })
                .defaultIfEmpty(
                        RateLimitResult.builder()
                                .allowed(false)
                                .remainingTokens(0)
                                .retryAfterSeconds(0)
                                .build()
                );
    }
}