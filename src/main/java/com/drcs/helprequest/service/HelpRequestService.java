package com.drcs.helprequest.service;

import com.drcs.helprequest.RequestStatus;
import com.drcs.helprequest.dto.CreateHelpRequestRequest;
import com.drcs.helprequest.dto.HelpRequestDto;

import java.util.List;
import java.util.UUID;

public interface HelpRequestService {

    HelpRequestDto createHelpRequest(CreateHelpRequestRequest request, String citizenEmail);

    HelpRequestDto getHelpRequestById(UUID id);

    List<HelpRequestDto> getMyHelpRequests(String citizenEmail);

    List<HelpRequestDto> getAllHelpRequests();

    List<HelpRequestDto> getPendingHelpRequestsSortedByPriority();

    HelpRequestDto assignVolunteer(UUID requestId, UUID volunteerId);

    HelpRequestDto updateRequestStatus(UUID requestId, RequestStatus status);
}