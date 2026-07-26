package com.yashdotdev.api_gateway.config;

import com.yashdotdev.api_gateway.rateLimiter.RateLimitRule;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rate-limit")
public class RateLimitProperties {

    private RateLimitRule login;
    private RateLimitRule register;
    private RateLimitRule createUrl;
    private RateLimitRule redirect;

}