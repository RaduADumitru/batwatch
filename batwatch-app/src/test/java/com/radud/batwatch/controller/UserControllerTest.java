package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateUserRequest;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.radud.batwatch.model.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @Test
    void testCreateUser() {
        CreateUserRequest request = new CreateUserRequest("testuser", "password123");
        AppUser createdUser = AppUser.builder()
                .id(1L)
                .username("testuser")
                .password("encodedPassword")
                .build();
        UserResponse userResponse = new UserResponse(1L, "testuser", Set.of(USER));

        when(userService.createUser(request.username(), request.password(), USER)).thenReturn(createdUser);
        when(userMapper.toResponse(createdUser)).thenReturn(userResponse);

        userController.createUser(request);

        assertEquals(1L, userResponse.id());
        assertEquals("testuser", userResponse.username());
        assertEquals(Set.of(USER), userResponse.roles());

        verify(userService).createUser(request.username(), request.password(), USER);
        verify(userMapper).toResponse(createdUser);
    }
}
