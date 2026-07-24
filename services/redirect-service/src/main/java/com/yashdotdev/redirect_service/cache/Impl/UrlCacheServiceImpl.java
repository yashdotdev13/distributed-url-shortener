package com.yashdotdev.redirect_service.cache.Impl;


import com.yashdotdev.redirect_service.cache.UrlCacheService;
import com.yashdotdev.redirect_service.constants.RedisConstants;
import com.yashdotdev.redirect_service.entity.Url;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlCacheServiceImpl implements UrlCacheService {

    private final CacheManager cacheManager;


    @Override
    public Optional<Url> get(String shortCode) {


        Cache cache = cacheManager.getCache(RedisConstants.URL_CACHE);

        if(cache == null){
            return Optional.empty();
        }

        Url url = cache.get(shortCode, Url.class);
        if(url!=null) {

            log.info("""
                            
                            Redis Cache HIT
                            
                            Short Code : {}
                            
                            """,
                    shortCode
            );

            return Optional.of(url);
        }
            log.info("""
                
                Redis Cache MISS
                
                Short Code : {}
                
                """,
                    shortCode
            );
        return Optional.empty();
    }

    @Override
    public void put(Url url) {

        Cache cache = cacheManager.getCache(RedisConstants.URL_CACHE);

        if(cache != null){
            cache.put(url.getShortCode(),url);

              log.info("""
                    
                    URL Cached
                    
                    Short Code : {}
                    
                    """,
                    url.getShortCode()
            );
        }
    }

    @Override
    public void evict(String shortCode) {


        Cache cache = cacheManager.getCache(RedisConstants.URL_CACHE);

        if(cache!= null){
            cache.evict(shortCode);

            log.info("""
                    
                    Cache Evicted
                    
                    Short Code : {}
                    
                    """,
                    shortCode
            );
        }
    }
}
