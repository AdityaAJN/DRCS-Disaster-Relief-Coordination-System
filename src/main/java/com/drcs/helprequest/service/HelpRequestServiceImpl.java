package com.drcs.helprequest.service;

import com.drcs.disaster.Disaster;
import com.drcs.disaster.DisasterRepository;
import com.drcs.exception.ResourceNotFoundException;
import com.drcs.helprequest.HelpCategory;
import com.drcs.helprequest.HelpRequest;
import com.drcs.helprequest.HelpRequestRepository;
import com.drcs.helprequest.RequestStatus;
import com.drcs.helprequest.dto.CreateHelpRequestRequest;
import com.drcs.helprequest.dto.HelpRequestDto;
import com.drcs.helprequest.mapper.HelpRequestMapper;
import com.drcs.user.User;
import com.drcs.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HelpRequestServiceImpl implements HelpRequestService {

    private final HelpRequestRepository helpRequestRepository;
    private final UserRepository userRepository;
    private final DisasterRepository disasterRepository;
    private final HelpRequestMapper helpRequestMapper;

    @Override
    @Transactional
    public HelpRequestDto createHelpRequest(CreateHelpRequestRequest request, String citizenEmail) {
        User citizen = userRepository.findByEmail(citizenEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen user not found: " + citizenEmail));

        Disaster disaster = null;
        if (request.getDisasterId() != null) {
            disaster = disasterRepository.findById(request.getDisasterId()).orElse(null);
        }

        int priorityScore = calculatePriorityScore(request.getCategory(), request.getFamilyMembersCount());

        HelpRequest helpRequest = HelpRequest.builder()
                .citizen(citizen)
                .disaster(disaster)
                .category(request.getCategory())
                .priorityScore(priorityScore)
                .status(RequestStatus.PENDING)
                .description(request.getDescription())
                .familyMembersCount(request.getFamilyMembersCount())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        HelpRequest saved = helpRequestRepository.save(helpRequest);
        return helpRequestMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public HelpRequestDto getHelpRequestById(UUID id) {
        HelpRequest helpRequest = helpRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Help request not found with ID: " + id));
        return helpRequestMapper.toDto(helpRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HelpRequestDto> getMyHelpRequests(String citizenEmail) {
        User citizen = userRepository.findByEmail(citizenEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen not found: " + citizenEmail));

        return helpRequestRepository.findByCitizen(citizen).stream()
                .map(helpRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HelpRequestDto> getAllHelpRequests() {
        return helpRequestRepository.findAll().stream()
                .map(helpRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HelpRequestDto> getPendingHelpRequestsSortedByPriority() {
        return helpRequestRepository.findByStatusOrderByPriorityScoreDesc(RequestStatus.PENDING).stream()
                .map(helpRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HelpRequestDto assignVolunteer(UUID requestId, UUID volunteerId) {
        HelpRequest helpRequest = helpRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Help request not found: " + requestId));

        User volunteer = userRepository.findById(volunteerId)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer user not found: " + volunteerId));

        helpRequest.setAssignedVolunteer(volunteer);
        helpRequest.setStatus(RequestStatus.ASSIGNED);

        HelpRequest updated = helpRequestRepository.save(helpRequest);
        return helpRequestMapper.toDto(updated);
    }

    @Override
    @Transactional
    public HelpRequestDto updateRequestStatus(UUID requestId, RequestStatus status) {
        HelpRequest helpRequest = helpRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Help request not found: " + requestId));

        helpRequest.setStatus(status);
        HelpRequest updated = helpRequestRepository.save(helpRequest);
        return helpRequestMapper.toDto(updated);
    }

    private int calculatePriorityScore(HelpCategory category, int familyMembers) {
        int baseCategoryScore = switch (category) {
            case EMERGENCY, RESCUE -> 5;
            case MEDICINE -> 4;
            case SHELTER -> 3;
            case FOOD, WATER -> 2;
        };

        int familyWeight = Math.min(familyMembers, 5);
        return Math.min(10, baseCategoryScore + familyWeight);
    }
}