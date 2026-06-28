package com.drcs.shelter.dto;

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
public class ShelterDto {

    private UUID id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer totalCapacity;
    private Integer currentOccupancy;
    private Integer availableSpace;
    private boolean hasFood;
    private boolean hasMedical;
    private UUID managedById;
    private String managedByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}