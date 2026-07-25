package com.yashdotdev.analytic_service.service.Impl;

import com.yashdotdev.analytic_service.entity.ownership.UrlOwnership;
import com.yashdotdev.analytic_service.repository.UrlOwnershipRepository;
import com.yashdotdev.analytic_service.service.UrlOwnershipService;
import com.yashdotdev.common.events.UrlCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlOwnershipServiceImpl
        implements UrlOwnershipService {

    private final UrlOwnershipRepository urlOwnershipRepository;

    @Override
    public void saveOwnership(
            UrlCreatedEvent event
    ) {

        log.info("""

                Saving URL Ownership

                Short Code : {}
                Owner Id   : {}

                """,
                event.shortCode(),
                event.userId()
        );

        UrlOwnership ownership = UrlOwnership.builder()
                .shortCode(event.shortCode())
                .ownerId(event.userId())
                .build();

        urlOwnershipRepository.save(ownership);

        log.info("""

                URL Ownership Saved Successfully

                Short Code : {}

                """,
                event.shortCode()
        );
    }
}