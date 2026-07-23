package com.yashdotdev.url_service.repository;


import com.yashdotdev.url_service.entity.Url;
import com.yashdotdev.url_service.enums.UrlStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    /**
     * Used during redirect.
     */
    Optional<Url> findByShortCode(String shortCode);

    /**
     * Used to check duplicate short codes.
     */
    boolean existsByShortCode(String shortCode);

    /**
     * Dashboard - all URLs of a user.
     */
    List<Url> findAllByUserId(Long userId);

    /**
     * Dashboard with status filtering.
     */
    List<Url> findAllByUserIdAndStatus(
            Long userId,
            UrlStatus status
    );

    /**
     * Future optimization:
     * Avoid creating duplicate short URLs
     * for the same original URL.
     */
    Optional<Url> findByOriginalUrlAndUserId(
            String originalUrl,
            Long userId
    );
}
