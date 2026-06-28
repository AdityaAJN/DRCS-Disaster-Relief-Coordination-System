package com.drcs.user.controller;

import com.drcs.common.response.ApiResponse;
import com.drcs.user.dto.UpdateProfileRequest;
import com.drcs.user.dto.UserDto;
import com.drcs.user.service.UserService;
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

/**
 * REST Controller providing user profile management and admin user administration endpoints.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management API", description = "Endpoints for user profile retrieval, updates, and admin user controls")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get currently authenticated user profile")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserDto userDto = userService.getCurrentUserProfile(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(userDto, "Profile retrieved successfully"));
    }

    @PutMapping("/me")
    @Operation(summary = "Update authenticated user profile details")
    public ResponseEntity<ApiResponse<UserDto>> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateProfileRequest request) {
        UserDto updatedUser = userService.updateProfile(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success(updatedUser, "Profile updated successfully"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get list of all registered users (Admin Only)")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users, "Users retrieved successfully"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user details by ID (Admin Only)")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable UUID id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(userDto, "User details retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deactivate user account by ID (Admin Only)")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable UUID id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok(ApiResponse.success("User account deactivated successfully"));
    }
}