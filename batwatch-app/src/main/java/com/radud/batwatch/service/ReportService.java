package com.radud.batwatch.service;

import com.radud.batwatch.exception.ReportNotFoundException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.model.Status;
import com.radud.batwatch.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.radud.batwatch.model.StatusType.NEW;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    private final UserContextService userContextService;

    @Transactional
    public Report createReport(Report report) {
        AppUser currentUser = userContextService.getCurrentUser();
        // assign status new
        Status status = Status.builder()
                .statusType(NEW)
                .setByUser(currentUser)
                .report(report)
                .build();
        report.getStatuses().add(status);
        // report is created by the current user
        report.setCreatedByUser(currentUser);
        return reportRepository.save(report);
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("Report not found with id: " + id));
    }
}
