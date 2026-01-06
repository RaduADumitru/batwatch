package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateAdminRequest;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.radud.batwatch.model.Role.ADMIN;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseBody
    public UserResponse createAdmin(@Valid @RequestBody CreateAdminRequest request) {
        AppUser createdUser = userService.createUser(request.username(), request.password(), ADMIN);
        return userMapper.toResponse(createdUser);
    }
}