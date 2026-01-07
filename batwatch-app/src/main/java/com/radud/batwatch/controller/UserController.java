package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateUserRequest;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.radud.batwatch.model.Role.USER;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "User", description = "User management endpoints")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Operation(summary = "Create user", description = "Creates a new user with the USER role, with the provided username and password. This user will then be able to do actions like leaving reports.")
    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        AppUser createdUser = userService.createUser(request.username(), request.password(), USER);
        return userMapper.toResponse(createdUser);
    }
}
