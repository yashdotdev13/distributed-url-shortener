package com.yashdotdev.redirect_service.constants;


public final class RedisConstants {

    private RedisConstants() {
    }

    public static final String URL_CACHE = "url-cache";

    public static final String REDIS_KEY_PREFIX = "url:";

    public static final long URL_CACHE_TTL_MINUTES = 30;
}
