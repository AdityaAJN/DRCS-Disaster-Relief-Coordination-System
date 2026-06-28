package com.drcs.shelter.controller;

import com.drcs.common.response.ApiResponse;
import com.drcs.shelter.dto.CreateShelterRequest;
import com.drcs.shelter.dto.ShelterDto;
import com.drcs.shelter.service.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for relief shelter creation, occupancy tracking, and resource availability updates.
 */
@RestController
@RequestMapping("/shelters")
@RequiredArgsConstructor
@Tag(name = "Shelter Management API", description = "Endpoints for disaster relief shelter management and occupancy tracking")
public class ShelterController {

    private final ShelterService shelterService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'NGO')")
    @Operation(summary = "Create a new relief shelter (Admin/NGO)")
    public ResponseEntity<ApiResponse<ShelterDto>> createShelter(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateShelterRequest request) {
        ShelterDto shelter = shelterService.createShelter(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(shelter, "Shelter registered successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NGO')")
    @Operation(summary = "Update shelter details")
    public ResponseEntity<ApiResponse<ShelterDto>> updateShelter(
            @PathVariable UUID id,
            @Valid @RequestBody CreateShelterRequest request) {
        ShelterDto shelter = shelterService.updateShelter(id, request);
        return ResponseEntity.ok(ApiResponse.success(shelter, "Shelter details updated successfully"));
    }

    @PatchMapping("/{id}/occupancy")
    @PreAuthorize("hasAnyRole('ADMIN', 'NGO', 'VOLUNTEER')")
    @Operation(summary = "Update current shelter live occupancy count")
    public ResponseEntity<ApiResponse<ShelterDto>> updateOccupancy(
            @PathVariable UUID id,
            @RequestParam int occupancy) {
        ShelterDto shelter = shelterService.updateOccupancy(id, occupancy);
        return ResponseEntity.ok(ApiResponse.success(shelter, "Shelter occupancy updated successfully"));
    }

    @GetMapping
    @Operation(summary = "Get all registered relief shelters (Public/Authenticated)")
    public ResponseEntity<ApiResponse<List<ShelterDto>>> getAllShelters() {
        List<ShelterDto> shelters = shelterService.getAllShelters();
        return ResponseEntity.ok(ApiResponse.success(shelters, "Shelters retrieved successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get shelter details by ID")
    public ResponseEntity<ApiResponse<ShelterDto>> getShelterById(@PathVariable UUID id) {
        ShelterDto shelter = shelterService.getShelterById(id);
        return ResponseEntity.ok(ApiResponse.success(shelter, "Shelter details retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a shelter record (Admin Only)")
    public ResponseEntity<ApiResponse<Void>> deleteShelter(@PathVariable UUID id) {
        shelterService.deleteShelter(id);
        return ResponseEntity.ok(ApiResponse.success("Shelter record deleted successfully"));
    }
}