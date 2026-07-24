package com.yashdotdev.redirect_service.service;

import jakarta.servlet.http.HttpServletRequest;

public interface RedirectService {

    String resolveOriginalUrl(String shortCode, HttpServletRequest request);
}
