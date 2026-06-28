package com.drcs.volunteer.dto;

import com.drcs.volunteer.VerificationStatus;
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
public class VolunteerProfileDto {

    private UUID id;
    private UUID userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String skills;
    private boolean isAvailable;
    private Double currentLatitude;
    private Double currentLongitude;
    private LocalDateTime lastLocationUpdate;
    private VerificationStatus verificationStatus;
}