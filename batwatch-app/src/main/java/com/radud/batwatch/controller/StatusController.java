package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.StatusMapper;
import com.radud.batwatch.model.Status;
import com.radud.batwatch.request.CreateStatusRequest;
import com.radud.batwatch.response.StatusResponse;
import com.radud.batwatch.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
@AllArgsConstructor
@Tag(name = "Status", description = "Status management endpoints")
public class StatusController {

    private final StatusService statusService;

    private final StatusMapper statusMapper;

    @Operation(summary = "Create status", description = "Assign a status to a report. Requires VOLUNTEER role.")
    @PreAuthorize("hasRole('VOLUNTEER')")
    @PostMapping
    public StatusResponse createStatus(@Valid @RequestBody CreateStatusRequest request) {
        Status status = statusService.createStatus(request.reportId(), request.statusType());
        return statusMapper.toResponse(status);
    }
}
