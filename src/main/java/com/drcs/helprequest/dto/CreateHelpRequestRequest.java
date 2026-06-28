package com.drcs.helprequest.dto;

import com.drcs.helprequest.HelpCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateHelpRequestRequest {

    private UUID disasterId;

    @NotNull(message = "Help category is required")
    private HelpCategory category;

    private String description;

    @NotNull(message = "Family members count is required")
    @Min(value = 1, message = "Family members count must be at least 1")
    private Integer familyMembersCount;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;
}