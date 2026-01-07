package com.radud.batwatch.service;

import com.radud.batwatch.exception.AuthenticatedUserNotFoundException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserContextServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserContextService userContextService;

    @Mock
    Authentication auth;

    @Mock
    UserDetails userDetails;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    AppUser currentUser = AppUser.builder()
            .id(1L)
            .username("testuser")
            .password("password")
            .build();

    @Test
    void getCurrentUser_noAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(null);
        SecurityContextHolder.setContext(securityContext);

        assertNull(userContextService.getCurrentUser());
    }

    @Test
    void getCurrentUser_userNotAuthenticated() {
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(false);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        assertNull(userContextService.getCurrentUser());
    }

    @Test
    void getCurrentUser_principalIsUserDetails() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(currentUser));

        assertSame(currentUser, userContextService.getCurrentUser());
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void getCurrentUser_principalIsString() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getPrincipal()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(currentUser));

        assertSame(currentUser, userContextService.getCurrentUser());
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void getCurrentUser_userNotFound() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getPrincipal()).thenReturn("unknownuser");
        when(userRepository.findByUsername("unknownuser")).thenReturn(Optional.empty());

        assertThrows(AuthenticatedUserNotFoundException.class,
                () -> userContextService.getCurrentUser());
    }
}
