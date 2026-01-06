package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateUserRequest;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.radud.batwatch.model.Role.USER;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    @ResponseBody
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        AppUser createdUser = userService.createUser(request.username(), request.password(), USER);
        return userMapper.toResponse(createdUser);
    }
}
