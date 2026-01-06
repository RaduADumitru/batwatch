package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.StatusMapper;
import com.radud.batwatch.model.Status;
import com.radud.batwatch.request.CreateStatusRequest;
import com.radud.batwatch.response.StatusResponse;
import com.radud.batwatch.service.StatusService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@AllArgsConstructor
public class StatusController {

    private final StatusService statusService;

    private final StatusMapper statusMapper;

    @PreAuthorize("hasRole('VOLUNTEER')")
    @PostMapping
    public ResponseEntity<?> createStatus(@Valid @RequestBody CreateStatusRequest request) {
        Status status = statusService.createStatus(request.reportId(), request.statusType());
        StatusResponse response = statusMapper.toResponse(status);
        return ResponseEntity.ok(response);
    }
}
