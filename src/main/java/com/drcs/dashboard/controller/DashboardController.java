package com.drcs.dashboard.controller;

import com.drcs.common.response.ApiResponse;
import com.drcs.dashboard.dto.DashboardAnalyticsDto;
import com.drcs.dashboard.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard Analytics API", description = "Endpoints for retrieving operational KPIs, shelter occupancy rates, and disaster response statistics")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/analytics")
    @PreAuthorize("hasAnyRole('ADMIN', 'NGO')")
    @Operation(summary = "Get real-time operational analytics, metrics, and occupancy ratios")
    public ResponseEntity<ApiResponse<DashboardAnalyticsDto>> getAnalytics() {
        DashboardAnalyticsDto analytics = dashboardService.getSystemAnalytics();
        return ResponseEntity.ok(ApiResponse.success(analytics, "System analytics retrieved successfully"));
    }
}
