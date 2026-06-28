package com.drcs.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardAnalyticsDto {

    private long totalUsers;
    private long totalCitizens;
    private long totalVolunteers;
    private long totalNgos;
    
    private long activeDisastersCount;
    private long totalHelpRequestsCount;
    private long pendingHelpRequestsCount;
    private long resolvedHelpRequestsCount;
    
    private long totalSheltersCount;
    private int totalShelterCapacity;
    private int currentShelterOccupancy;
    private double shelterOccupancyRatePercentage;
}