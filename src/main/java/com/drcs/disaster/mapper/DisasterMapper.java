package com.drcs.disaster.mapper;

import com.drcs.disaster.Disaster;
import com.drcs.disaster.dto.DisasterDto;
import org.springframework.stereotype.Component;

@Component
public class DisasterMapper {

    public DisasterDto toDto(Disaster disaster) {
        if (disaster == null) {
            return null;
        }

        return DisasterDto.builder()
                .id(disaster.getId())
                .title(disaster.getTitle())
                .description(disaster.getDescription())
                .severityLevel(disaster.getSeverityLevel())
                .status(disaster.getStatus())
                .latitude(disaster.getLatitude())
                .longitude(disaster.getLongitude())
                .radiusKm(disaster.getRadiusKm())
                .createdById(disaster.getCreatedBy() != null ? disaster.getCreatedBy().getId() : null)
                .createdByName(disaster.getCreatedBy() != null ? disaster.getCreatedBy().getFullName() : null)
                .createdAt(disaster.getCreatedAt())
                .updatedAt(disaster.getUpdatedAt())
                .build();
    }
}