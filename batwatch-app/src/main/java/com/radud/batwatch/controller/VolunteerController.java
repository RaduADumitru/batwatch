package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Role;
import com.radud.batwatch.request.CreateVolunteerRequest;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteer")
@AllArgsConstructor
public class VolunteerController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseBody
    public UserResponse createVolunteer(@Valid @RequestBody CreateVolunteerRequest request) {
        AppUser createdUser = userService.createUser(request.username(), request.password(), Role.VOLUNTEER);
        return userMapper.toResponse(createdUser);
    }
}
