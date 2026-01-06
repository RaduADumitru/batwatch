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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignment")
@AllArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    private final AssignmentMapper assignmentMapper;

    @PreAuthorize("hasRole('VOLUNTEER')")
    @PostMapping
    @ResponseBody
    public AssignmentResponse createAssignment(@Valid @RequestBody CreateAssignmentRequest request) {
        Assignment assignment = assignmentService.createAssignment(request.reportId(), request.assignedUserId());
        return assignmentMapper.toResponse(assignment);
    }
}
