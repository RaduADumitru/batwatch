package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.AssignmentMapper;
import com.radud.batwatch.model.Assignment;
import com.radud.batwatch.request.CreateAssignmentRequest;
import com.radud.batwatch.response.AssignmentResponse;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignment")
@AllArgsConstructor
@Tag(name = "Assignment", description = "Assignment management endpoints")
public class AssignmentController {

    private final AssignmentService assignmentService;

    private final AssignmentMapper assignmentMapper;

    @Operation(summary = "Create assignment", description = "Create a new assignment for a report (assign a report to a given user). Requires VOLUNTEER role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created assignment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Request lacks valid authentication credentials",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authenticated user does not have required VOLUNTEER role",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Assigned user or report not found",
                    content = @Content(mediaType = "text/plain", examples = { @ExampleObject(value = "Invalid report id: 213521") }))
    })
    @PreAuthorize("hasRole('VOLUNTEER')")
    @PostMapping
    public AssignmentResponse createAssignment(@Valid @RequestBody CreateAssignmentRequest request) {
        Assignment assignment = assignmentService.createAssignment(request.reportId(), request.assignedUserId());
        return assignmentMapper.toResponse(assignment);
    }
}
