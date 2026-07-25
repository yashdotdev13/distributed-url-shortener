package com.yashdotdev.analytic_service.service;


import com.yashdotdev.common.events.UrlCreatedEvent;

public interface UrlOwnershipService {

    void saveOwnership(
            UrlCreatedEvent event
    );
}
