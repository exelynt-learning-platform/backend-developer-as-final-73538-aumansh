package com.example.bookingsystem.security;

import org.springframework.security.core.Authentication;

public class AuthUtil {
    public static String getUsername(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new org.springframework.security.access.AccessDeniedException("User is not authenticated");
        }
        return authentication.getName();
    }

    public static boolean isAdmin(Authentication authentication) {
        if (authentication == null) return false;
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
