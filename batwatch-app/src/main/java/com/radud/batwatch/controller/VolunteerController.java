package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.VolunteerMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateVolunteerRequest;
import com.radud.batwatch.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    private final UserService userService;

    private final VolunteerMapper volunteerMapper;

    public VolunteerController(UserService userService, VolunteerMapper volunteerMapper) {
        this.userService = userService;
        this.volunteerMapper = volunteerMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createVolunteer(@Valid @RequestBody CreateVolunteerRequest request) {
        AppUser user = volunteerMapper.createRequestToModel(request);
        userService.createUser(user);
        return ResponseEntity.ok("Volunteer created");
    }
}
