package com.drcs.helprequest.mapper;

import com.drcs.helprequest.HelpRequest;
import com.drcs.helprequest.dto.HelpRequestDto;
import org.springframework.stereotype.Component;

@Component
public class HelpRequestMapper {

    public HelpRequestDto toDto(HelpRequest helpRequest) {
        if (helpRequest == null) {
            return null;
        }

        return HelpRequestDto.builder()
                .id(helpRequest.getId())
                .citizenId(helpRequest.getCitizen() != null ? helpRequest.getCitizen().getId() : null)
                .citizenName(helpRequest.getCitizen() != null ? helpRequest.getCitizen().getFullName() : null)
                .citizenPhone(helpRequest.getCitizen() != null ? helpRequest.getCitizen().getPhoneNumber() : null)
                .disasterId(helpRequest.getDisaster() != null ? helpRequest.getDisaster().getId() : null)
                .disasterTitle(helpRequest.getDisaster() != null ? helpRequest.getDisaster().getTitle() : null)
                .category(helpRequest.getCategory())
                .priorityScore(helpRequest.getPriorityScore())
                .status(helpRequest.getStatus())
                .description(helpRequest.getDescription())
                .familyMembersCount(helpRequest.getFamilyMembersCount())
                .latitude(helpRequest.getLatitude())
                .longitude(helpRequest.getLongitude())
                .assignedVolunteerId(helpRequest.getAssignedVolunteer() != null ? helpRequest.getAssignedVolunteer().getId() : null)
                .assignedVolunteerName(helpRequest.getAssignedVolunteer() != null ? helpRequest.getAssignedVolunteer().getFullName() : null)
                .createdAt(helpRequest.getCreatedAt())
                .updatedAt(helpRequest.getUpdatedAt())
                .build();
    }
}
