package com.yashdotdev.redirect_service.bloom;

public interface BloomFilterService {

    void put(String shortCode);

    boolean mightContain(String shortCode);

    void initialize();

    void rebuild();
}
