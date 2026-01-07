package com.radud.batwatch.service;

import com.radud.batwatch.exception.ReportNotFoundException;
import com.radud.batwatch.exception.UserNotFoundException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Assignment;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.repository.AssignmentRepository;
import com.radud.batwatch.repository.ReportRepository;
import com.radud.batwatch.repository.UserRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    private final UserContextService userContextService;

    private final ReportRepository reportRepository;

    private final UserRepository userRepository;

    @Transactional
    public Assignment createAssignment(@Nonnull Long reportId, @Nonnull Long assignedUserId) {
        // get current user
        AppUser currentUser = userContextService.getCurrentUser();
        // check if report id is valid
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("Invalid report id: " + reportId));
        // check if assigned user id is valid
        AppUser assignedUser = userRepository.findById(assignedUserId)
                .orElseThrow(() -> new UserNotFoundException("Invalid assigned user id: " + assignedUserId));
        // instantiate object and save
        Assignment assignment = Assignment.builder()
                .report(report)
                .assignedByUser(currentUser)
                .assignedUser(assignedUser)
                .build();
        return assignmentRepository.save(assignment);
    }
}
