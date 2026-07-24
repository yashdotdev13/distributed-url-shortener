package com.yashdotdev.redirect_service.service;


public interface CacheEvictionService {

    void evict(String shortCode);

}