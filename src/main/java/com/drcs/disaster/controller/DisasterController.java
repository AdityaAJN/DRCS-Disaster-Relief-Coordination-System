package com.drcs.disaster.controller;

import com.drcs.common.response.ApiResponse;
import com.drcs.disaster.DisasterStatus;
import com.drcs.disaster.dto.CreateDisasterRequest;
import com.drcs.disaster.dto.DisasterDto;
import com.drcs.disaster.service.DisasterService;
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

@RestController
@RequestMapping("/disasters")
@RequiredArgsConstructor
@Tag(name = "Disaster Management API", description = "Endpoints for disaster event tracking and management")
public class DisasterController {

    private final DisasterService disasterService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new disaster event (Admin Only)")
    public ResponseEntity<ApiResponse<DisasterDto>> createDisaster(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateDisasterRequest request) {
        DisasterDto disaster = disasterService.createDisaster(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(disaster, "Disaster event created successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update disaster event details (Admin Only)")
    public ResponseEntity<ApiResponse<DisasterDto>> updateDisaster(
            @PathVariable UUID id,
            @Valid @RequestBody CreateDisasterRequest request) {
        DisasterDto disaster = disasterService.updateDisaster(id, request);
        return ResponseEntity.ok(ApiResponse.success(disaster, "Disaster event updated successfully"));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update disaster status (ACTIVE, CONTAINED, RESOLVED) (Admin Only)")
    public ResponseEntity<ApiResponse<DisasterDto>> updateStatus(
            @PathVariable UUID id,
            @RequestParam DisasterStatus status) {
        DisasterDto disaster = disasterService.updateDisasterStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(disaster, "Disaster status updated successfully"));
    }

    @GetMapping
    @Operation(summary = "Get all registered disaster events (Public/Authenticated)")
    public ResponseEntity<ApiResponse<List<DisasterDto>>> getAllDisasters() {
        List<DisasterDto> disasters = disasterService.getAllDisasters();
        return ResponseEntity.ok(ApiResponse.success(disasters, "Disasters retrieved successfully"));
    }

    @GetMapping("/active")
    @Operation(summary = "Get all currently active disaster events")
    public ResponseEntity<ApiResponse<List<DisasterDto>>> getActiveDisasters() {
        List<DisasterDto> disasters = disasterService.getActiveDisasters();
        return ResponseEntity.ok(ApiResponse.success(disasters, "Active disasters retrieved successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get disaster details by ID")
    public ResponseEntity<ApiResponse<DisasterDto>> getDisasterById(@PathVariable UUID id) {
        DisasterDto disaster = disasterService.getDisasterById(id);
        return ResponseEntity.ok(ApiResponse.success(disaster, "Disaster details retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a disaster event record (Admin Only)")
    public ResponseEntity<ApiResponse<Void>> deleteDisaster(@PathVariable UUID id) {
        disasterService.deleteDisaster(id);
        return ResponseEntity.ok(ApiResponse.success("Disaster record deleted successfully"));
    }
}
