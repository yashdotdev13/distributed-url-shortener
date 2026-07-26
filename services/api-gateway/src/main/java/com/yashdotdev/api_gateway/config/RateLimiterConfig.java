package com.yashdotdev.api_gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.List;

@Configuration
public class RateLimiterConfig {

    @Bean
    public DefaultRedisScript<List> tokenBucketScript() {

        DefaultRedisScript<List> script =
                new DefaultRedisScript<>();

        script.setLocation(
                new ClassPathResource(
                        "scripts/distributed-token-bucket.lua"
                )
        );

        script.setResultType(List.class);

        return script;
    }
}