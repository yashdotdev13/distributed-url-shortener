package com.yashdotdev.redirect_service.service.Impl;

import com.yashdotdev.common.events.ClickEvents;
import com.yashdotdev.redirect_service.cache.UrlCacheService;
import com.yashdotdev.redirect_service.entity.Url;
import com.yashdotdev.redirect_service.enums.UrlStatus;
import com.yashdotdev.redirect_service.exceptions.ShortUrlNotFoundException;
import com.yashdotdev.redirect_service.exceptions.UrlExpiredException;
import com.yashdotdev.redirect_service.producer.ClickEventProducer;
import com.yashdotdev.redirect_service.repository.UrlRepository;
import com.yashdotdev.redirect_service.service.RedirectService;
import com.yashdotdev.redirect_service.util.RequestMetadataExtractor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedirectServiceImpl implements RedirectService {

    private final UrlRepository urlRepository;
    private final UrlCacheService urlCacheService;
    private final ClickEventProducer clickEventProducer;
    private final RequestMetadataExtractor metadataExtractor;

    @Override
    public String resolveOriginalUrl(
            String shortCode,
            HttpServletRequest request
    ) {

        log.info("""

                Resolving Short URL

                Short Code : {}

                """,
                shortCode
        );

        Optional<Url> cachedUrl = urlCacheService.get(shortCode);

        if (cachedUrl.isPresent()) {
            Url url = cachedUrl.get();
            validateExpiration(url);

            publishClickEvent(url, request);

            log.info("""
                    Returning URL from Redis
                    Original URL : {}
                    """,
                    url.getOriginalUrl()
            );

            return url.getOriginalUrl();
        }

        Url url = urlRepository
                .findByShortCodeAndStatus(
                        shortCode,
                        UrlStatus.ACTIVE
                )
                .orElseThrow(() ->
                        new ShortUrlNotFoundException(shortCode)
                );

        validateExpiration(url);

        urlCacheService.put(url);

        publishClickEvent(url, request);

        log.info("""

                Returning URL from Database

                Original URL : {}

                """,
                url.getOriginalUrl()
        );

        return url.getOriginalUrl();
    }

    private void validateExpiration(Url url) {

        if (url.getExpiresAt() != null &&
                url.getExpiresAt().isBefore(Instant.now())) {

            throw new UrlExpiredException(url.getShortCode());
        }
    }

    private void publishClickEvent(
            Url url,
            HttpServletRequest request
    ) {

        log.info("""
                        
                        Publishing Click Event
                        
                        Short Code : {}
                        
                        """,
                url.getShortCode()
        );

        ClickEvents event = ClickEvents.builder()
                .shortCode(url.getShortCode())
                .originalUrl(url.getOriginalUrl())
                .userId(url.getUserId())
                .ipAddress(
                        metadataExtractor.getIpAddress(request)
                )
                .userAgent(
                        metadataExtractor.getUserAgent(request)
                )
                .referer(
                        metadataExtractor.getReferer(request)
                )
                .clickedAt(Instant.now())
                .build();

        log.info("""
                        
                        Click Event Metadata
                        
                        User-Agent : {}
                        IP Address : {}
                        Referer    : {}
                        
                        """,
                event.userAgent(),
                event.ipAddress(),
                event.referer()
        );

        clickEventProducer.publish(event);
    }
}