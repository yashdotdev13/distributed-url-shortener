package com.yashdotdev.api_gateway.rateLimiter;

public final class RateLimitKeyGenerator {

    private RateLimitKeyGenerator() {
    }

    public static String login(String ip) {
        return "rate_limit:login:" + ip;
    }

    public static String register(String ip) {
        return "rate_limit:register:" + ip;
    }

    public static String createUrl(Long userId) {
        return "rate_limit:create_url:" + userId;
    }

    public static String redirect(String ip) {
        return "rate_limit:redirect:" + ip;
    }

}
