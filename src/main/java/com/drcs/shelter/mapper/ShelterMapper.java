package com.drcs.shelter.mapper;

import com.drcs.shelter.Shelter;
import com.drcs.shelter.dto.ShelterDto;
import org.springframework.stereotype.Component;

@Component
public class ShelterMapper {

    public ShelterDto toDto(Shelter shelter) {
        if (shelter == null) {
            return null;
        }

        int availableSpace = Math.max(0, shelter.getTotalCapacity() - shelter.getCurrentOccupancy());

        return ShelterDto.builder()
                .id(shelter.getId())
                .name(shelter.getName())
                .address(shelter.getAddress())
                .latitude(shelter.getLatitude())
                .longitude(shelter.getLongitude())
                .totalCapacity(shelter.getTotalCapacity())
                .currentOccupancy(shelter.getCurrentOccupancy())
                .availableSpace(availableSpace)
                .hasFood(shelter.isHasFood())
                .hasMedical(shelter.isHasMedical())
                .managedById(shelter.getManagedBy() != null ? shelter.getManagedBy().getId() : null)
                .managedByName(shelter.getManagedBy() != null ? shelter.getManagedBy().getFullName() : null)
                .createdAt(shelter.getCreatedAt())
                .updatedAt(shelter.getUpdatedAt())
                .build();
    }
}