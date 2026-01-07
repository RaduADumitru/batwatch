package com.radud.batwatch.service;

import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Role;
import com.radud.batwatch.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static com.radud.batwatch.model.Role.ADMIN;
import static com.radud.batwatch.model.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void testLoadUserByUsername_userNotFound() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        assertThrows(
            UsernameNotFoundException.class,
            () -> customUserDetailsService.loadUserByUsername("user")
        );
    }

    @Test
    void testLoadUserByUsername_userFound() {
        AppUser user = AppUser.builder()
                .id(1L)
                .username("user")
                .password("password")
                .roles(Set.of(USER))
                .build();

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("user");

        assertEquals("user", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
    }
}
