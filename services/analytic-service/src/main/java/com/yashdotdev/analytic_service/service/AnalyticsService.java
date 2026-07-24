package com.yashdotdev.analytic_service.service;

import com.yashdotdev.common.events.ClickEvents;

public interface AnalyticsService {

    void saveClickEvent(ClickEvents event);

}
