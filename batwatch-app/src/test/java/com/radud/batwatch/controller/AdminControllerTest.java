package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateAdminRequest;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.radud.batwatch.model.Role.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AdminController adminController;

    @Test
    void testCreateAdmin() {
        CreateAdminRequest request = new CreateAdminRequest("testadmin", "password123");
        AppUser createdUser = AppUser.builder()
                .id(1L)
                .username("testadmin")
                .password("encodedPassword")
                .build();
        UserResponse userResponse = new UserResponse(1L, "testadmin", Set.of(ADMIN));

        when(userService.createUser(request.username(), request.password(), ADMIN)).thenReturn(createdUser);
        when(userMapper.toResponse(createdUser)).thenReturn(userResponse);

        adminController.createAdmin(request);

        assertEquals(1L, userResponse.id());
        assertEquals("testadmin", userResponse.username());
        assertEquals(Set.of(ADMIN), userResponse.roles());

        verify(userService).createUser(request.username(), request.password(), ADMIN);
        verify(userMapper).toResponse(createdUser);
        verifyNoMoreInteractions(userService, userMapper);
    }
}
