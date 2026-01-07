package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateUserRequest;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "An user with the same username already exists",
                    content = @Content(mediaType = "text/plain", examples = { @ExampleObject(value = "User named user1 already exists") })) })
    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        AppUser createdUser = userService.createUser(request.username(), request.password(), USER);
        return userMapper.toResponse(createdUser);
    }
}
