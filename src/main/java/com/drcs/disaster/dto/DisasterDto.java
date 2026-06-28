package com.drcs.disaster.dto;

import com.drcs.disaster.DisasterStatus;
import com.drcs.disaster.SeverityLevel;
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
public class DisasterDto {

    private UUID id;
    private String title;
    private String description;
    private SeverityLevel severityLevel;
    private DisasterStatus status;
    private Double latitude;
    private Double longitude;
    private Double radiusKm;
    private UUID createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}