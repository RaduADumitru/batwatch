package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.UserMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Role;
import com.radud.batwatch.request.CreateVolunteerRequest;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created volunteer",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class,
                                    example = "{\"id\":123,\"username\":\"volunteer1\",\"roles\":[\"VOLUNTEER\"]}")) }),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Request lacks valid authentication credentials",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authenticated user does not have required ADMIN role",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "An user with the same username already exists",
                    content = @Content(mediaType = "text/plain", examples = { @ExampleObject(value = "User named volunteer1 already exists") })) })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserResponse createVolunteer(@Valid @RequestBody CreateVolunteerRequest request) {
        AppUser createdUser = userService.createUser(request.username(), request.password(), Role.VOLUNTEER);
        return userMapper.toResponse(createdUser);
    }
}
