package com.yashdotdev.url_service.service.Impl;


import com.yashdotdev.url_service.dtos.CreateShortUrlRequest;
import com.yashdotdev.url_service.dtos.ShortUrlResponse;
import com.yashdotdev.url_service.entity.Url;
import com.yashdotdev.url_service.generator.ShortCodeGenerator;
import com.yashdotdev.url_service.mapper.UrlMapper;
import com.yashdotdev.url_service.repository.UrlRepository;
import com.yashdotdev.url_service.security.AuthenticatedUser;
import com.yashdotdev.url_service.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private final UrlMapper urlMapper;

    @Override
    public ShortUrlResponse createShortUrl(
            CreateShortUrlRequest request,
            AuthenticatedUser currentUser
    ) {

        log.info("""
                

                Creating Short URL

                User Id      : {}
                Original URL : {}

                """,
                currentUser.userId(),
                request.originalUrl()
        );

        String shortCode = generateUniqueShortCode();

        Url url = urlMapper.toEntity(
                request,
                currentUser.userId(),
                shortCode
        );

        log.info("""
==========================
Mapped Entity
==========================
Original URL : {}
Short Code   : {}
User Id      : {}
Status       : {}
Click Count  : {}
==========================
""",
                url.getOriginalUrl(),
                url.getShortCode(),
                url.getUserId(),
                url.getStatus(),
                url.getClickCount()
        );


        Url savedUrl = urlRepository.save(url);

        log.info("""
                
                URL Created Successfully

                URL Id     : {}
                Short Code : {}

                """,
                savedUrl.getId(),
                savedUrl.getShortCode()
        );

        return urlMapper.toShortUrlResponse(savedUrl);
    }

    /**
     * Generates a unique short code.
     * Today this is mostly a safeguard.
     * Tomorrow, with Snowflake IDs, collisions
     * should be practically impossible.
     */
    private String generateUniqueShortCode() {

        String shortCode;

        do {

            shortCode = shortCodeGenerator.generate();

        } while (urlRepository.existsByShortCode(shortCode));

        return shortCode;
    }

}