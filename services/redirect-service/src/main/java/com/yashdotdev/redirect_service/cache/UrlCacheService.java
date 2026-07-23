package com.yashdotdev.redirect_service.cache;



import com.yashdotdev.redirect_service.entity.Url;

import java.util.Optional;

public interface UrlCacheService {

    Optional<Url> get(String shortCode);

    void put(Url url);

    void evict(String shortCode);
}