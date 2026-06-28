package com.drcs.shelter.service;

import com.drcs.exception.BusinessValidationException;
import com.drcs.exception.ResourceNotFoundException;
import com.drcs.shelter.Shelter;
import com.drcs.shelter.ShelterRepository;
import com.drcs.shelter.dto.CreateShelterRequest;
import com.drcs.shelter.dto.ShelterDto;
import com.drcs.shelter.mapper.ShelterMapper;
import com.drcs.user.User;
import com.drcs.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation managing relief shelter allocations and occupancy limits.
 */
@Service
@RequiredArgsConstructor
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository shelterRepository;
    private final UserRepository userRepository;
    private final ShelterMapper shelterMapper;

    @Override
    @Transactional
    public ShelterDto createShelter(CreateShelterRequest request, String managerEmail) {
        User manager = userRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Manager user not found: " + managerEmail));

        Shelter shelter = Shelter.builder()
                .name(request.getName())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .totalCapacity(request.getTotalCapacity())
                .currentOccupancy(0)
                .hasFood(request.isHasFood())
                .hasMedical(request.isHasMedical())
                .managedBy(manager)
                .build();

        Shelter saved = shelterRepository.save(shelter);
        return shelterMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ShelterDto updateShelter(UUID id, CreateShelterRequest request) {
        Shelter shelter = shelterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shelter not found with ID: " + id));

        shelter.setName(request.getName());
        shelter.setAddress(request.getAddress());
        shelter.setLatitude(request.getLatitude());
                shelter.setTotalCapacity(request.getTotalCapacity());
        shelter.setHasFood(request.isHasFood());
        shelter.setHasMedical(request.isHasMedical());

        Shelter updated = shelterRepository.save(shelter);
        return shelterMapper.toDto(updated);
    }

    @Override
    @Transactional
    public ShelterDto updateOccupancy(UUID id, int occupancy) {
        Shelter shelter = shelterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shelter not found with ID: " + id));

        if (occupancy > shelter.getTotalCapacity()) {
            throw new BusinessValidationException("Current occupancy cannot exceed total shelter capacity (" + shelter.getTotalCapacity() + ")");
        }

        shelter.setCurrentOccupancy(Math.max(0, occupancy));
        Shelter updated = shelterRepository.save(shelter);
        return shelterMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ShelterDto getShelterById(UUID id) {
        Shelter shelter = shelterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shelter not found with ID: " + id));
        return shelterMapper.toDto(shelter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShelterDto> getAllShelters() {
        return shelterRepository.findAll().stream()
                .map(shelterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteShelter(UUID id) {
        Shelter shelter = shelterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shelter not found with ID: " + id));
        shelterRepository.delete(shelter);
    }
}