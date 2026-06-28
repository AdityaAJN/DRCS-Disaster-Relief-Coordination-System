package com.drcs.helprequest.controller;

import com.drcs.common.response.ApiResponse;
import com.drcs.helprequest.RequestStatus;
import com.drcs.helprequest.dto.CreateHelpRequestRequest;
import com.drcs.helprequest.dto.HelpRequestDto;
import com.drcs.helprequest.service.HelpRequestService;
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
@RequestMapping("/help-requests")
@RequiredArgsConstructor
@Tag(name = "Help Requests API", description = "Endpoints for citizens to lodge emergency requests and admins/volunteers to manage dispatch")
public class HelpRequestController {

    private final HelpRequestService helpRequestService;

    @PostMapping
    @PreAuthorize("hasRole('CITIZEN') or hasRole('ADMIN')")
    @Operation(summary = "Lodge a new emergency help request (Citizen)")
    public ResponseEntity<ApiResponse<HelpRequestDto>> createHelpRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateHelpRequestRequest request) {
        HelpRequestDto response = helpRequestService.createHelpRequest(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Emergency help request submitted successfully"));
    }

    @GetMapping("/my-requests")
    @PreAuthorize("hasRole('CITIZEN')")
    @Operation(summary = "Get help requests lodged by the logged-in citizen")
    public ResponseEntity<ApiResponse<List<HelpRequestDto>>> getMyRequests(@AuthenticationPrincipal UserDetails userDetails) {
        List<HelpRequestDto> requests = helpRequestService.getMyHelpRequests(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(requests, "User help requests retrieved successfully"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER', 'NGO')")
    @Operation(summary = "Get all system help requests")
    public ResponseEntity<ApiResponse<List<HelpRequestDto>>> getAllRequests() {
        List<HelpRequestDto> requests = helpRequestService.getAllHelpRequests();
        return ResponseEntity.ok(ApiResponse.success(requests, "Help requests retrieved successfully"));
    }

    @GetMapping("/priority")
    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    @Operation(summary = "Get pending requests sorted by AI priority score (High to Low)")
    public ResponseEntity<ApiResponse<List<HelpRequestDto>>> getPriorityRequests() {
        List<HelpRequestDto> requests = helpRequestService.getPendingHelpRequestsSortedByPriority();
        return ResponseEntity.ok(ApiResponse.success(requests, "Priority-ordered requests retrieved successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get help request details by ID")
    public ResponseEntity<ApiResponse<HelpRequestDto>> getRequestById(@PathVariable UUID id) {
        HelpRequestDto request = helpRequestService.getHelpRequestById(id);
        return ResponseEntity.ok(ApiResponse.success(request, "Request details retrieved successfully"));
    }

    @PutMapping("/{id}/assign")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign a volunteer to an emergency help request (Admin Only)")
    public ResponseEntity<ApiResponse<HelpRequestDto>> assignVolunteer(
            @PathVariable UUID id,
            @RequestParam UUID volunteerId) {
        HelpRequestDto request = helpRequestService.assignVolunteer(id, volunteerId);
        return ResponseEntity.ok(ApiResponse.success(request, "Volunteer assigned successfully"));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    @Operation(summary = "Update help request status (PENDING, ASSIGNED, IN_PROGRESS, RESOLVED, CANCELLED)")
    public ResponseEntity<ApiResponse<HelpRequestDto>> updateStatus(
            @PathVariable UUID id,
            @RequestParam RequestStatus status) {
        HelpRequestDto request = helpRequestService.updateRequestStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(request, "Request status updated successfully"));
    }
}
