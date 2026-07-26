package com.yashdotdev.redirect_service.bloom.Impl;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.yashdotdev.redirect_service.bloom.BloomFilterService;
import com.yashdotdev.redirect_service.repository.UrlRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class BloomFilterServiceImpl
        implements BloomFilterService {


    private static final long EXPECTED_INSERTIONS = 1_000_000;
    private static final double FALSE_POSITIVE_RATE = 0.01;
    private final UrlRepository urlRepository;
    private BloomFilter<String> bloomFilter;


    @PostConstruct
    @Override
    public void initialize() {
        log.info("""
                Initializing Bloom Filter...
                """);

        bloomFilter = BloomFilter.create(
                Funnels.stringFunnel(StandardCharsets.UTF_8),
                EXPECTED_INSERTIONS,
                FALSE_POSITIVE_RATE
        );

        urlRepository.findAll()
                .forEach(url ->
                        bloomFilter.put(
                                url.getShortCode()
                        )
                );

        log.info("""
                Bloom Filter Initialized Successfully
                Total URLs Loaded : {}
                """,
                urlRepository.count()
        );
    }

    @Override
    public void put(String shortCode) {
        bloomFilter.put(shortCode);
        log.info("""
                Bloom Filter Updated
                Short Code : {}
                """,
                shortCode
        );
    }

    @Override
    public boolean mightContain(String shortCode) {
        return bloomFilter.mightContain(shortCode);
    }

    @Override
    public void rebuild() {

        log.info("""
                Rebuilding Bloom Filter...
                """);
        initialize();
        log.info("""

                Bloom Filter Rebuilt Successfully

                """);
    }

}
