package com.drcs.volunteer.service;

import com.drcs.exception.ResourceNotFoundException;
import com.drcs.user.User;
import com.drcs.user.UserRepository;
import com.drcs.volunteer.VerificationStatus;
import com.drcs.volunteer.VolunteerProfile;
import com.drcs.volunteer.VolunteerRepository;
import com.drcs.volunteer.dto.UpdateVolunteerLocationRequest;
import com.drcs.volunteer.dto.VolunteerProfileDto;
import com.drcs.volunteer.mapper.VolunteerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final UserRepository userRepository;
    private final VolunteerMapper volunteerMapper;

    @Override
    @Transactional
    public VolunteerProfileDto registerVolunteerProfile(String userEmail, String skills) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        VolunteerProfile profile = volunteerRepository.findByUser(user)
                .orElseGet(() -> VolunteerProfile.builder()
                        .user(user)
                        .skills(skills)
                        .isAvailable(true)
                        .verificationStatus(VerificationStatus.APPROVED)
                        .build());

        profile.setSkills(skills);
        VolunteerProfile saved = volunteerRepository.save(profile);
        return volunteerMapper.toDto(saved);
    }

    @Override
    @Transactional
    public VolunteerProfileDto updateAvailability(String userEmail, boolean isAvailable) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        VolunteerProfile profile = volunteerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer profile not found for user: " + userEmail));

        profile.setAvailable(isAvailable);
        VolunteerProfile updated = volunteerRepository.save(profile);
        return volunteerMapper.toDto(updated);
    }

    @Override
    @Transactional
    public VolunteerProfileDto updateLocation(String userEmail, UpdateVolunteerLocationRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        VolunteerProfile profile = volunteerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer profile not found for user: " + userEmail));

        profile.setCurrentLatitude(request.getLatitude());
        profile.setCurrentLongitude(request.getLongitude());
        profile.setLastLocationUpdate(LocalDateTime.now());

        VolunteerProfile updated = volunteerRepository.save(profile);
        return volunteerMapper.toDto(updated);
    }

    @Override
    @Transactional
    public VolunteerProfileDto verifyVolunteer(UUID volunteerProfileId, VerificationStatus status) {
        VolunteerProfile profile = volunteerRepository.findById(volunteerProfileId)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer profile not found with ID: " + volunteerProfileId));

        profile.setVerificationStatus(status);
        VolunteerProfile updated = volunteerRepository.save(profile);
        return volunteerMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public VolunteerProfileDto getMyProfile(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        VolunteerProfile profile = volunteerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer profile not found"));

        return volunteerMapper.toDto(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolunteerProfileDto> getAvailableVolunteers() {
        return volunteerRepository.findByIsAvailableAndVerificationStatus(true, VerificationStatus.APPROVED).stream()
                .map(volunteerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolunteerProfileDto> getAllVolunteers() {
        return volunteerRepository.findAll().stream()
                .map(volunteerMapper::toDto)
                .collect(Collectors.toList());
    }
}