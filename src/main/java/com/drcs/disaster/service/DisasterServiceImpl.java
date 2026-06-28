package com.drcs.disaster.service;

import com.drcs.disaster.Disaster;
import com.drcs.disaster.DisasterRepository;
import com.drcs.disaster.DisasterStatus;
import com.drcs.disaster.dto.CreateDisasterRequest;
import com.drcs.disaster.dto.DisasterDto;
import com.drcs.disaster.mapper.DisasterMapper;
import com.drcs.exception.ResourceNotFoundException;
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
public class DisasterServiceImpl implements DisasterService {

    private final DisasterRepository disasterRepository;
    private final UserRepository userRepository;
    private final DisasterMapper disasterMapper;

    @Override
    @Transactional
    public DisasterDto createDisaster(CreateDisasterRequest request, String userEmail) {
        User creator = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        Disaster disaster = Disaster.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .severityLevel(request.getSeverityLevel())
                .status(DisasterStatus.ACTIVE)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .radiusKm(request.getRadiusKm())
                .createdBy(creator)
                .build();

        Disaster savedDisaster = disasterRepository.save(disaster);
        return disasterMapper.toDto(savedDisaster);
    }

    @Override
    @Transactional
    public DisasterDto updateDisaster(UUID id, CreateDisasterRequest request) {
        Disaster disaster = disasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disaster not found with ID: " + id));

        disaster.setTitle(request.getTitle());
        disaster.setDescription(request.getDescription());
        disaster.setSeverityLevel(request.getSeverityLevel());
        disaster.setLatitude(request.getLatitude());
        disaster.setLongitude(request.getLongitude());
        disaster.setRadiusKm(request.getRadiusKm());

        Disaster updated = disasterRepository.save(disaster);
        return disasterMapper.toDto(updated);
    }

    @Override
    @Transactional
    public DisasterDto updateDisasterStatus(UUID id, DisasterStatus status) {
        Disaster disaster = disasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disaster not found with ID: " + id));

        disaster.setStatus(status);
        Disaster updated = disasterRepository.save(disaster);
        return disasterMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public DisasterDto getDisasterById(UUID id) {
        Disaster disaster = disasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disaster not found with ID: " + id));
        return disasterMapper.toDto(disaster);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisasterDto> getAllDisasters() {
        return disasterRepository.findAll().stream()
                .map(disasterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisasterDto> getActiveDisasters() {
        return disasterRepository.findByStatus(DisasterStatus.ACTIVE).stream()
                .map(disasterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteDisaster(UUID id) {
        Disaster disaster = disasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disaster not found with ID: " + id));
        disasterRepository.delete(disaster);
    }
}