package com.radud.batwatch.service;

import com.radud.batwatch.exception.DuplicateUserException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static com.radud.batwatch.model.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser_duplicateUsername() {
        AppUser existingUser = AppUser.builder()
                .id(1L)
                .username("existingUser")
                .password("encodedPassword")
                .build();

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingUser));

        assertThrows(DuplicateUserException.class,
                () -> userService.createUser("existingUser", "newPassword", null));
    }

    @Test
    void testCreateUser_success() {
        AppUser newUser = AppUser.builder()
                .username("newUser")
                .password("encodedPassword")
                .roles(Set.of(USER))
                .build();

        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(newUser);

        AppUser savedUser = userService.createUser("newUser", "newPassword", USER);

        ArgumentCaptor <AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository).save(captor.capture());
        AppUser userToBeSaved = captor.getValue();
        assertEquals("newUser", userToBeSaved.getUsername());
        assertEquals("encodedPassword", userToBeSaved.getPassword());
        assertEquals(Set.of(USER), userToBeSaved.getRoles());

        assertEquals("newUser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(Set.of(USER), savedUser.getRoles());
    }
}
