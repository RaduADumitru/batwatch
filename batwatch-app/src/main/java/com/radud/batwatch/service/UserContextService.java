package com.radud.batwatch.service;

import com.radud.batwatch.exception.AuthenticatedUserNotFoundException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.repository.UserRepository;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserContextService {

    private final UserRepository userRepository;

    /**
     * Retrieves the currently authenticated user from the security context.
     *
     * @return the current AppUser, or null if no user is authenticated
     * @throws AuthenticatedUserNotFoundException if no authenticated user is found or if the user does not exist in the database
     */
    @Nullable
    public AppUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
           return null;
        }

        Object principal = auth.getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticatedUserNotFoundException("Authenticated user not found in database: " + username));
    }
}
