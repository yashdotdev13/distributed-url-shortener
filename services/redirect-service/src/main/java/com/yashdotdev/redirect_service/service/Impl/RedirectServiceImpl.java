package com.yashdotdev.redirect_service.service.Impl;


import com.yashdotdev.redirect_service.cache.UrlCacheService;
import com.yashdotdev.redirect_service.entity.Url;
import com.yashdotdev.redirect_service.enums.UrlStatus;
import com.yashdotdev.redirect_service.exceptions.ShortUrlNotFoundException;
import com.yashdotdev.redirect_service.exceptions.UrlExpiredException;
import com.yashdotdev.redirect_service.repository.UrlRepository;
import com.yashdotdev.redirect_service.service.RedirectService;
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

    @Override
    public String resolveOriginalUrl(String shortCode) {

        log.info("""

            Resolving Short URL

            Short Code : {}

            """,
                shortCode
        );

        /*
         * Step 1 : Check Redis
         */
        Optional<Url> cachedUrl = urlCacheService.get(shortCode);

        if (cachedUrl.isPresent()) {

            Url url = cachedUrl.get();

            validateExpiration(url);

            log.info("""

                Returning URL from Redis

                Original URL : {}

                """,
                    url.getOriginalUrl()
            );

            return url.getOriginalUrl();
        }

        /*
         * Step 2 : Load from Database
         */
        Url url = urlRepository
                .findByShortCodeAndStatus(
                        shortCode,
                        UrlStatus.ACTIVE
                )
                .orElseThrow(() ->
                        new ShortUrlNotFoundException(shortCode)
                );

        validateExpiration(url);

        /*
         * Step 3 : Cache in Redis
         */
        urlCacheService.put(url);

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
}
