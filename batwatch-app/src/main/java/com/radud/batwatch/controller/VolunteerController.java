package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Role;
import com.radud.batwatch.request.CreateVolunteerRequest;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteer")
@AllArgsConstructor
@Tag(name = "Volunteer", description = "Volunteer management endpoints")
public class VolunteerController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Operation(summary = "Create volunteer", description = "Creates a new user with the VOLUNTEER role, with the provided username and password. Volunteers have all rights of standard users, plus abilities like managing assignments. Requires ADMIN role.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserResponse createVolunteer(@Valid @RequestBody CreateVolunteerRequest request) {
        AppUser createdUser = userService.createUser(request.username(), request.password(), Role.VOLUNTEER);
        return userMapper.toResponse(createdUser);
    }
}
