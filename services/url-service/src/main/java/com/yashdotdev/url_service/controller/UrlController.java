package com.yashdotdev.url_service.controller;

import com.yashdotdev.url_service.dtos.*;
import com.yashdotdev.url_service.security.AuthenticatedUser;
import com.yashdotdev.url_service.security.CurrentUserService;
import com.yashdotdev.url_service.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private final CurrentUserService currentUserService;

    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request,
            HttpServletRequest servletRequest
    ) {

        AuthenticatedUser currentUser =
                currentUserService.getCurrentUser(servletRequest);

        ShortUrlResponse response =
                urlService.createShortUrl(
                        request,
                        currentUser
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UrlDetailsResponse> getUrl(
            @PathVariable("id") Long id,
            @RequestHeader("X-User-Id") Long userId

    ) {

        return ResponseEntity.ok(
                urlService.getUrl(
                        id,
                        userId
                )
        );
    }

    @GetMapping
    public ResponseEntity<Page<UrlSummaryResponse>> getAllUrls(

            @RequestHeader("X-User-Id") Long userId,
            Pageable pageable

    ) {
        return ResponseEntity.ok(urlService.getAllUrls(userId,pageable)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ShortUrlResponse> updateUrl(

            @PathVariable("id") Long id,
            @RequestHeader("X-User-Id") Long userId,
            @Valid
            @RequestBody
            UpdateUrlRequest request

    ) {

        return ResponseEntity.ok(
                urlService.updateUrl(
                        id,
                        userId,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUrl(

            @PathVariable("id") Long id,
            @RequestHeader("X-User-Id") Long userId

    ) {

        urlService.deleteUrl(
                id,
                userId
        );
        return ResponseEntity.noContent().build();
    }
}