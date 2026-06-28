package com.drcs.dashboard.service;

import com.drcs.dashboard.dto.DashboardAnalyticsDto;
import com.drcs.disaster.DisasterRepository;
import com.drcs.disaster.DisasterStatus;
import com.drcs.helprequest.HelpRequestRepository;
import com.drcs.helprequest.RequestStatus;
import com.drcs.shelter.Shelter;
import com.drcs.shelter.ShelterRepository;
import com.drcs.user.Role;
import com.drcs.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final DisasterRepository disasterRepository;
    private final HelpRequestRepository helpRequestRepository;
    private final ShelterRepository shelterRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardAnalyticsDto getSystemAnalytics() {
        long totalUsers = userRepository.count();
        long totalCitizens = userRepository.findAll().stream().filter(u -> u.getRole() == Role.ROLE_CITIZEN).count();
        long totalVolunteers = userRepository.findAll().stream().filter(u -> u.getRole() == Role.ROLE_VOLUNTEER).count();
        long totalNgos = userRepository.findAll().stream().filter(u -> u.getRole() == Role.ROLE_NGO).count();

        long activeDisasters = disasterRepository.findByStatus(DisasterStatus.ACTIVE).size();
        long totalRequests = helpRequestRepository.count();
        long pendingRequests = helpRequestRepository.findByStatus(RequestStatus.PENDING).size();
        long resolvedRequests = helpRequestRepository.findByStatus(RequestStatus.RESOLVED).size();

        List<Shelter> shelters = shelterRepository.findAll();
        long totalShelters = shelters.size();
        int totalCapacity = shelters.stream().mapToInt(Shelter::getTotalCapacity).sum();
        int currentOccupancy = shelters.stream().mapToInt(Shelter::getCurrentOccupancy).sum();

        double occupancyRate = totalCapacity > 0 ? ((double) currentOccupancy / totalCapacity) * 100.0 : 0.0;

        return DashboardAnalyticsDto.builder()
                .totalUsers(totalUsers)
                .totalCitizens(totalCitizens)
                .totalVolunteers(totalVolunteers)
                .totalNgos(totalNgos)
                .activeDisastersCount(activeDisasters)
                .totalHelpRequestsCount(totalRequests)
                .pendingHelpRequestsCount(pendingRequests)
                .resolvedHelpRequestsCount(resolvedRequests)
                .totalSheltersCount(totalShelters)
                .totalShelterCapacity(totalCapacity)
                .currentShelterOccupancy(currentOccupancy)
                .shelterOccupancyRatePercentage(Math.round(occupancyRate * 100.0) / 100.0)
                .build();
    }
}