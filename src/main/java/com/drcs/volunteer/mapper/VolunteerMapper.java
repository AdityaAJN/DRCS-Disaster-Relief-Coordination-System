package com.drcs.volunteer.mapper;

import com.drcs.volunteer.VolunteerProfile;
import com.drcs.volunteer.dto.VolunteerProfileDto;
import org.springframework.stereotype.Component;

@Component
public class VolunteerMapper {

    public VolunteerProfileDto toDto(VolunteerProfile profile) {
        if (profile == null) {
            return null;
        }

        return VolunteerProfileDto.builder()
                .id(profile.getId())
                .userId(profile.getUser() != null ? profile.getUser().getId() : null)
                .fullName(profile.getUser() != null ? profile.getUser().getFullName() : null)
                .email(profile.getUser() != null ? profile.getUser().getEmail() : null)
                .phoneNumber(profile.getUser() != null ? profile.getUser().getPhoneNumber() : null)
                .skills(profile.getSkills())
                .isAvailable(profile.isAvailable())
                .currentLatitude(profile.getCurrentLatitude())
                .currentLongitude(profile.getCurrentLongitude())
                .lastLocationUpdate(profile.getLastLocationUpdate())
                .verificationStatus(profile.getVerificationStatus())
                .build();
    }
}