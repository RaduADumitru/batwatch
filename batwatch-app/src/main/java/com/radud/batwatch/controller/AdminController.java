package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateAdminRequest;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.radud.batwatch.model.Role.ADMIN;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@Tag(name = "Admin", description = "Admin user management endpoints")
public class AdminController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Operation(summary = "Create admin", description = "Creates a new admin user with the provided username and password. Requires ADMIN role. An admin has all rights of volunteers and additional administrative privileges like creating new volunteers and admins.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserResponse createAdmin(@Valid @RequestBody CreateAdminRequest request) {
        AppUser createdUser = userService.createUser(request.username(), request.password(), ADMIN);
        return userMapper.toResponse(createdUser);
    }
}