package com.drcs.volunteer.controller;

import com.drcs.common.response.ApiResponse;
import com.drcs.volunteer.VerificationStatus;
import com.drcs.volunteer.dto.UpdateVolunteerLocationRequest;
import com.drcs.volunteer.dto.VolunteerProfileDto;
import com.drcs.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/volunteers")
@RequiredArgsConstructor
@Tag(name = "Volunteer Management API", description = "Endpoints for volunteer location updates, availability toggles, and dispatch management")
public class VolunteerController {

    private final VolunteerService volunteerService;

    @PostMapping("/profile")
    @PreAuthorize("hasRole('VOLUNTEER') or hasRole('ADMIN')")
    @Operation(summary = "Create or update volunteer profile details")
    public ResponseEntity<ApiResponse<VolunteerProfileDto>> registerProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false, defaultValue = "General Rescue & Relief") String skills) {
        VolunteerProfileDto profile = volunteerService.registerVolunteerProfile(userDetails.getUsername(), skills);
        return ResponseEntity.ok(ApiResponse.success(profile, "Volunteer profile updated successfully"));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('VOLUNTEER')")
    @Operation(summary = "Get logged-in volunteer profile and availability status")
    public ResponseEntity<ApiResponse<VolunteerProfileDto>> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        VolunteerProfileDto profile = volunteerService.getMyProfile(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(profile, "Volunteer profile retrieved successfully"));
    }

    @PatchMapping("/availability")
    @PreAuthorize("hasRole('VOLUNTEER')")
    @Operation(summary = "Toggle volunteer availability status (available / unavailable)")
    public ResponseEntity<ApiResponse<VolunteerProfileDto>> updateAvailability(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam boolean available) {
        VolunteerProfileDto profile = volunteerService.updateAvailability(userDetails.getUsername(), available);
        return ResponseEntity.ok(ApiResponse.success(profile, "Availability status updated successfully"));
    }

    @PostMapping("/location")
    @PreAuthorize("hasRole('VOLUNTEER')")
    @Operation(summary = "Update live GPS location coordinates (Volunteer Mobile/Web)")
    public ResponseEntity<ApiResponse<VolunteerProfileDto>> updateLocation(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateVolunteerLocationRequest request) {
        VolunteerProfileDto profile = volunteerService.updateLocation(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success(profile, "GPS location updated successfully"));
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('ADMIN', 'NGO')")
    @Operation(summary = "Get list of all active and available verified volunteers")
    public ResponseEntity<ApiResponse<List<VolunteerProfileDto>>> getAvailableVolunteers() {
        List<VolunteerProfileDto> volunteers = volunteerService.getAvailableVolunteers();
        return ResponseEntity.ok(ApiResponse.success(volunteers, "Available volunteers retrieved successfully"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get list of all registered volunteers (Admin Only)")
    public ResponseEntity<ApiResponse<List<VolunteerProfileDto>>> getAllVolunteers() {
        List<VolunteerProfileDto> volunteers = volunteerService.getAllVolunteers();
        return ResponseEntity.ok(ApiResponse.success(volunteers, "Volunteers retrieved successfully"));
    }

    @PatchMapping("/{id}/verify")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Verify or reject a volunteer registration (Admin Only)")
    public ResponseEntity<ApiResponse<VolunteerProfileDto>> verifyVolunteer(
            @PathVariable UUID id,
            @RequestParam VerificationStatus status) {
        VolunteerProfileDto profile = volunteerService.verifyVolunteer(id, status);
        return ResponseEntity.ok(ApiResponse.success(profile, "Volunteer verification status updated successfully"));
    }
}