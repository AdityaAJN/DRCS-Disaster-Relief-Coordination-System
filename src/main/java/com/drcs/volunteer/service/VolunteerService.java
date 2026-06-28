package com.drcs.volunteer.service;

import com.drcs.volunteer.VerificationStatus;
import com.drcs.volunteer.dto.UpdateVolunteerLocationRequest;
import com.drcs.volunteer.dto.VolunteerProfileDto;

import java.util.List;
import java.util.UUID;

public interface VolunteerService {

    VolunteerProfileDto registerVolunteerProfile(String userEmail, String skills);

    VolunteerProfileDto updateAvailability(String userEmail, boolean isAvailable);

    VolunteerProfileDto updateLocation(String userEmail, UpdateVolunteerLocationRequest request);

    VolunteerProfileDto verifyVolunteer(UUID volunteerProfileId, VerificationStatus status);

    VolunteerProfileDto getMyProfile(String userEmail);

    List<VolunteerProfileDto> getAvailableVolunteers();

    List<VolunteerProfileDto> getAllVolunteers();
}