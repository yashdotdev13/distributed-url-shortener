package com.yashdotdev.redirect_service.service.Impl;


import com.yashdotdev.redirect_service.cache.UrlCacheService;
import com.yashdotdev.redirect_service.service.CacheEvictionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheEvictServiceImpl implements CacheEvictionService {

    private final UrlCacheService urlCacheService;

    @Override
    public void evict(String shortCode) {

        log.info("""
                Evicting Redis Cache
                Short Code : {}
                """,
                shortCode
        );

        urlCacheService.evict(shortCode);

        log.info("""
                Redis Cache Evicted Successfully
                Short Code : {}
                """,
                shortCode
        );
    }
}