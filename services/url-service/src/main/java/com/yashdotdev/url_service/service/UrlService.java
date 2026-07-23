package com.yashdotdev.url_service.service;

import com.yashdotdev.url_service.dtos.CreateShortUrlRequest;
import com.yashdotdev.url_service.dtos.ShortUrlResponse;
import com.yashdotdev.url_service.security.AuthenticatedUser;

public interface UrlService {


    ShortUrlResponse createShortUrl(CreateShortUrlRequest request,
                                    AuthenticatedUser currentUser);
}
