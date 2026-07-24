package com.yashdotdev.redirect_service.service.Impl;


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

@Service
@Slf4j
@RequiredArgsConstructor
public class RedirectServiceImpl implements RedirectService {

    private final UrlRepository urlRepository;

    @Override
    public String resolveOriginalUrl(String shortCode) {


        log.info("""
                
                Resolving Short URL
                
                Short Code : {}
          
                """,
                shortCode
        );

        Url url = urlRepository.findByShortCodeAndStatus(shortCode, UrlStatus.ACTIVE)
                .orElseThrow(()->
                        new ShortUrlNotFoundException(shortCode));

        if(url.getExpiresAt() !=null &&
        url.getExpiresAt().isBefore(Instant.now())){

            throw new UrlExpiredException(shortCode);
        }

        log.info("""
                
                Short URL Resolved
                
                Original URL : {}
                
                """,
                url.getOriginalUrl()
        );

        return url.getOriginalUrl();

    }
}
