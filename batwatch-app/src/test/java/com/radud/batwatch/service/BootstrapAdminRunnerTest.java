package com.radud.batwatch.service;

import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.Set;

import static com.radud.batwatch.model.Role.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BootstrapAdminRunnerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void run_adminNotPresent() {
        String username = "adminUser";
        String rawPassword = "adminPass";
        String encodedPassword = "encodedPass";

        BootstrapAdminRunner runner = new BootstrapAdminRunner(userRepository, passwordEncoder);

        // inject @Value fields, normally done by Spring
        ReflectionTestUtils.setField(runner, "adminUsername", username);
        ReflectionTestUtils.setField(runner, "adminPassword", rawPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        runner.run(null);

        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository, times(1)).save(captor.capture());

        AppUser saved = captor.getValue();
        assertEquals(username, saved.getUsername());
        assertEquals(encodedPassword, saved.getPassword());
        assertEquals(Set.of(ADMIN), saved.getRoles());
    }

    @Test
    void run_adminAlreadyPresent() {
        String username = "adminUser";

        BootstrapAdminRunner runner = new BootstrapAdminRunner(userRepository, passwordEncoder);

        // inject @Value fields, normally done by Spring
        ReflectionTestUtils.setField(runner, "adminUsername", username);
        ReflectionTestUtils.setField(runner, "adminPassword", "adminPass");

        AppUser existingAdmin = AppUser.builder()
                .id(1L)
                .username(username)
                .password("somePassword")
                .roles(Set.of(ADMIN))
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingAdmin));

        runner.run(null);

        verify(userRepository, never()).save(any());
    }
}
