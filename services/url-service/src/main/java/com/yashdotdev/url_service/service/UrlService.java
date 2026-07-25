package com.yashdotdev.url_service.service;

import com.yashdotdev.url_service.dtos.*;
import com.yashdotdev.url_service.security.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UrlService {


    ShortUrlResponse createShortUrl(CreateShortUrlRequest request,
                                    AuthenticatedUser currentUser);


    UrlDetailsResponse getUrl(
            Long id,
            Long userId
    );


    Page<UrlSummaryResponse> getAllUrls(Long userId, Pageable pageable);


    ShortUrlResponse updateUrl(Long id, Long userId, UpdateUrlRequest request);

    void deleteUrl(Long id, Long userId);
}
