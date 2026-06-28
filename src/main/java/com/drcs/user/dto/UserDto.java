package com.drcs.user.dto;

import com.drcs.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object representing user profile details returned by management endpoints.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Role role;
    private boolean isVerified;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}