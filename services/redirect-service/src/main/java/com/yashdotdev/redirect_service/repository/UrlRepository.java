package com.yashdotdev.redirect_service.repository;


import com.yashdotdev.redirect_service.entity.Url;
import com.yashdotdev.redirect_service.enums.UrlStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortCodeAndStatus(
            String shortCode,
            UrlStatus status
    );

}