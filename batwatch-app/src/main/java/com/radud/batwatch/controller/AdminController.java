package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.AdminMapper;
import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateAdminRequest;
import com.radud.batwatch.request.CreateUserRequest;
import com.radud.batwatch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final AdminMapper adminMapper;

    public AdminController(UserService userService, AdminMapper adminMapper) {
        this.userService = userService;
        this.adminMapper = adminMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody CreateAdminRequest request) {
        AppUser user = adminMapper.createRequestToModel(request);
        userService.createUser(user);
        return ResponseEntity.ok("Admin created");
    }
}