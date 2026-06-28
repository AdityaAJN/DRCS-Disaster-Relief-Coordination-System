package com.drcs.user.service;

import com.drcs.user.dto.UpdateProfileRequest;
import com.drcs.user.dto.UserDto;

import java.util.List;
import java.util.UUID;

/**
 * Service interface defining user management and profile operation contracts.
 */
public interface UserService {

    UserDto getCurrentUserProfile(String email);

    UserDto getUserById(UUID userId);

    List<UserDto> getAllUsers();

    UserDto updateProfile(String email, UpdateProfileRequest request);

    void deactivateUser(UUID userId);
}