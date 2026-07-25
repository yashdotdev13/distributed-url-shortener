package com.yashdotdev.url_service.service;

import com.yashdotdev.url_service.dtos.CreateShortUrlRequest;
import com.yashdotdev.url_service.dtos.ShortUrlResponse;
import com.yashdotdev.url_service.dtos.UrlDetailsResponse;
import com.yashdotdev.url_service.dtos.UrlSummaryResponse;
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
}
