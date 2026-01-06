package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateAdminRequest;
import com.radud.batwatch.request.CreateVolunteerRequest;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.radud.batwatch.model.Role.ADMIN;
import static com.radud.batwatch.model.Role.VOLUNTEER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VolunteerControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private VolunteerController volunteerController;

    @Test
    void testCreateVolunteer() {
        CreateVolunteerRequest request = new CreateVolunteerRequest("testvolunteer", "password123");
        AppUser createdUser = AppUser.builder()
                .id(1L)
                .username("testvolunteer")
                .password("encodedPassword")
                .build();
        UserResponse userResponse = new UserResponse(1L, "testvolunteer", Set.of(VOLUNTEER));

        when(userService.createUser(request.username(), request.password(), VOLUNTEER)).thenReturn(createdUser);
        when(userMapper.toResponse(createdUser)).thenReturn(userResponse);

        volunteerController.createVolunteer(request);

        assertEquals(1L, userResponse.id());
        assertEquals("testvolunteer", userResponse.username());
        assertEquals(Set.of(VOLUNTEER), userResponse.roles());

        verify(userService).createUser(request.username(), request.password(), VOLUNTEER);
        verify(userMapper).toResponse(createdUser);
    }
}
