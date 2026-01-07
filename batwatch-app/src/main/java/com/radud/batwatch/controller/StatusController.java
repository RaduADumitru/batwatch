package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.StatusMapper;
import com.radud.batwatch.model.Status;
import com.radud.batwatch.request.CreateStatusRequest;
import com.radud.batwatch.response.StatusResponse;
import com.radud.batwatch.service.StatusService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@AllArgsConstructor
@Tag(name = "Status", description = "Status management endpoints")
public class StatusController {

    private final StatusService statusService;

    private final StatusMapper statusMapper;

    @Operation(summary = "Create status", description = "Assign a status to a report. Requires VOLUNTEER role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created status",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Request lacks valid authentication credentials",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authenticated user does not have required VOLUNTEER role",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Report not found",
                    content = @Content(mediaType = "text/plain", examples = { @ExampleObject(value = "Invalid report id: 213521") }))
    })
    @PreAuthorize("hasRole('VOLUNTEER')")
    @PostMapping
    public StatusResponse createStatus(@Valid @RequestBody CreateStatusRequest request) {
        Status status = statusService.createStatus(request.reportId(), request.statusType());
        return statusMapper.toResponse(status);
    }
}
