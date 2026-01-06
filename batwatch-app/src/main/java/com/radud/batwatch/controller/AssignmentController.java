package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.AssignmentMapper;
import com.radud.batwatch.model.Assignment;
import com.radud.batwatch.request.CreateAssignmentRequest;
import com.radud.batwatch.response.AssignmentResponse;
import com.radud.batwatch.service.AssignmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assignment")
@AllArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    private final AssignmentMapper assignmentMapper;

    @PostMapping
    @PreAuthorize("hasRole('VOLUNTEER')")
    public ResponseEntity<?> createAssignment(@Valid @RequestBody CreateAssignmentRequest request) {
        Assignment assignment = assignmentService.createAssignment(request.reportId(), request.assignedUserId());
        AssignmentResponse response = assignmentMapper.toResponse(assignment);
        return ResponseEntity.ok(response);
    }
}
