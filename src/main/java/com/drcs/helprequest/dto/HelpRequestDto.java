package com.drcs.helprequest.dto;

import com.drcs.helprequest.HelpCategory;
import com.drcs.helprequest.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpRequestDto {

    private UUID id;
    private UUID citizenId;
    private String citizenName;
    private String citizenPhone;
    private UUID disasterId;
    private String disasterTitle;
    private HelpCategory category;
    private Integer priorityScore;
    private RequestStatus status;
    private String description;
    private Integer familyMembersCount;
    private Double latitude;
    private Double longitude;
    private UUID assignedVolunteerId;
    private String assignedVolunteerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}