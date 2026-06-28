package com.drcs.disaster.service;

import com.drcs.disaster.DisasterStatus;
import com.drcs.disaster.dto.CreateDisasterRequest;
import com.drcs.disaster.dto.DisasterDto;

import java.util.List;
import java.util.UUID;

public interface DisasterService {

    DisasterDto createDisaster(CreateDisasterRequest request, String userEmail);

    DisasterDto updateDisaster(UUID id, CreateDisasterRequest request);

    DisasterDto updateDisasterStatus(UUID id, DisasterStatus status);

    DisasterDto getDisasterById(UUID id);

    List<DisasterDto> getAllDisasters();

    List<DisasterDto> getActiveDisasters();

    void deleteDisaster(UUID id);
}