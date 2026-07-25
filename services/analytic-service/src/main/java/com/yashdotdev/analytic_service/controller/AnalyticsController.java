package com.yashdotdev.analytic_service.controller;

import com.yashdotdev.analytic_service.dtos.dashboard.AnalyticsDashboardResponse;
import com.yashdotdev.analytic_service.security.AuthenticatedUser;
import com.yashdotdev.analytic_service.security.CurrentUserService;
import com.yashdotdev.analytic_service.service.AnalyticsDashboardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
@Slf4j
public class AnalyticsController {

    private final AnalyticsDashboardService analyticsDashboardService;
    private final CurrentUserService currentUserService;

    @GetMapping("/{shortCode}/dashboard")
    public AnalyticsDashboardResponse getDashboard(

            @PathVariable("shortCode") String shortCode,

            HttpServletRequest servletRequest

    ) {

        AuthenticatedUser currentUser =
                currentUserService.getCurrentUser(servletRequest);

        log.info("""
                Fetching Analytics Dashboard

                Short Code : {}
                User Id    : {}

                """,
                shortCode,
                currentUser.userId()
        );

        AnalyticsDashboardResponse response =
                analyticsDashboardService.getDashboard(
                        shortCode,
                        currentUser
                );

        log.info("""
                Analytics Dashboard Retrieved Successfully

                Short Code : {}
                User Id    : {}

                """,
                shortCode,
                currentUser.userId()
        );

        return response;
    }
}