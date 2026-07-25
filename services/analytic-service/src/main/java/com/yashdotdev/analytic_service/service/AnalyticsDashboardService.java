package com.yashdotdev.analytic_service.service;

import com.yashdotdev.analytic_service.dtos.dashboard.AnalyticsDashboardResponse;
import com.yashdotdev.analytic_service.security.AuthenticatedUser;

public interface AnalyticsDashboardService {

    AnalyticsDashboardResponse getDashboard(
            String shortCode,
            AuthenticatedUser currentUser
    );
}
