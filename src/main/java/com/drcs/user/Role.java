package com.drcs.user;

/**
 * Enumeration representing Role-Based Access Control (RBAC) privileges across DRCS.
 * Prefixed with 'ROLE_' to adhere strictly to Spring Security standards.
 */
public enum Role {
    ROLE_CITIZEN,
    ROLE_VOLUNTEER,
    ROLE_NGO,
    ROLE_ADMIN
}