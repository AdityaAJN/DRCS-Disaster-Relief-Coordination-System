package com.drcs.shelter.service;

import com.drcs.shelter.dto.CreateShelterRequest;
import com.drcs.shelter.dto.ShelterDto;

import java.util.List;
import java.util.UUID;

public interface ShelterService {

    ShelterDto createShelter(CreateShelterRequest request, String managerEmail);

    ShelterDto updateShelter(UUID id, CreateShelterRequest request);

    ShelterDto updateOccupancy(UUID id, int occupancy);

    ShelterDto getShelterById(UUID id);

    List<ShelterDto> getAllShelters();

    void deleteShelter(UUID id);
}