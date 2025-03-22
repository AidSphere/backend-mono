package org.spring.authenticationservice.utils;


import org.spring.authenticationservice.exception.AppExceptions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for accessing security context information
 */
public class SecurityContextUtils {

    /**
     * Get the email of the currently authenticated user
     *
     * @return The current user's email
     * @throws AppExceptions.AuthenticationException if no authenticated user is found
     */
    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppExceptions.AuthenticationException("User not authenticated");
        }

        return authentication.getName();
    }

    /**
     * Check if the current user has admin role
     *
     * @return true if the current user has ROLE_ADMIN authority
     */
    public static boolean isCurrentUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    /**
     * Check if the current user has drug importer role
     *
     * @return true if the current user has ROLE_DRUG_IMPORTER authority
     */
    public static boolean isCurrentUserDrugImporter() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_DRUG_IMPORTER"));
    }
}
