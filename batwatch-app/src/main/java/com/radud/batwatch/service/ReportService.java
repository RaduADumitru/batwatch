package com.radud.batwatch.service;

import com.radud.batwatch.exception.CityNotFoundException;
import com.radud.batwatch.exception.ReportNotFoundException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.City;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.model.Status;
import com.radud.batwatch.repository.CityRepository;
import com.radud.batwatch.repository.ReportRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.radud.batwatch.model.StatusType.NEW;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    private final UserContextService userContextService;

    private final CityRepository cityRepository;

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

    public List<Report> getReports(@Nullable Long cityId) {
        if (cityId != null) {
            City city = cityRepository.findById(cityId)
                    .orElseThrow(() -> new CityNotFoundException("Invalid city id: " + cityId));
            Double longitude = city.getCenterPoint().getX();
            Double latitude = city.getCenterPoint().getY();
            Long radiusInMeters = city.getRadiusInMeters();
            return reportRepository.findByCityId(longitude, latitude, radiusInMeters);
        } else {
            return reportRepository.findAll();
        }
    }
}
