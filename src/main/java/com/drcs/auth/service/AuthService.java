package com.drcs.auth.service;

import com.drcs.auth.dto.AuthResponse;
import com.drcs.auth.dto.LoginRequest;
import com.drcs.auth.dto.RefreshTokenRequest;
import com.drcs.auth.dto.RegisterRequest;

/**
 * Service interface defining authentication business operations contracts.
 */
public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);
}
