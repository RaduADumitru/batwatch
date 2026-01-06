package com.radud.batwatch.service;

import com.radud.batwatch.exception.ReportNotFoundException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.model.Status;
import com.radud.batwatch.model.StatusType;
import com.radud.batwatch.repository.ReportRepository;
import com.radud.batwatch.repository.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatusService {

    private final UserContextService userContextService;

    private final StatusRepository statusRepository;

    private final ReportRepository reportRepository;

    public Status createStatus(Long reportId, StatusType statusType) {
        AppUser currentUser = userContextService.getCurrentUser();
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("Invalid report id: " + reportId));
        Status status = Status.builder()
                .report(report)
                .setByUser(currentUser)
                .statusType(statusType)
                .build();
        return statusRepository.save(status);
    }
}
