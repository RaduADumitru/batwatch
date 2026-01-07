package com.radud.batwatch.service;

import com.radud.batwatch.exception.AuthenticatedUserNotFoundException;
import com.radud.batwatch.exception.CityNotFoundException;
import com.radud.batwatch.exception.ReportNotFoundException;
import com.radud.batwatch.model.*;
import com.radud.batwatch.repository.CityRepository;
import com.radud.batwatch.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserContextService userContextService;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private ReportService reportService;

    @Mock
    private Point locationPoint;

    Location inputLocation = Location.builder()
            .details("Sample location")
            .locationPoint(locationPoint)
            .build();

    Report inputReport = Report.builder()
            .title("Test report")
            .description("Description")
            .locations(List.of(inputLocation))
            .statuses(new ArrayList<>())
            .build();

    @Mock
    private Point cityCenterPoint;

    @Test
    void testCreateReport_authenticatedUserNotFound() {
        when(userContextService.getCurrentUser()).thenThrow(new AuthenticatedUserNotFoundException("Authenticated user not found"));

        assertThrows(AuthenticatedUserNotFoundException.class,
                () -> reportService.createReport(inputReport));
    }

    @Test
    void testCreateReport_success() {
        AppUser currentUser = AppUser.builder()
                .id(1L)
                .username("testuser")
                .build();

        Instant createdOn = Instant.now();
        Status addedStatus = Status.builder()
                .id(1L)
                .setAt(createdOn)
                .setByUser(currentUser)
                .statusType(StatusType.NEW)
                .report(inputReport)
                .build();
        Report savedReport = Report.builder()
                .id(1L)
                .title("Test report")
                .description("Description")
                .locations(List.of(inputLocation))
                .createdOn(createdOn)
                .statuses(List.of(addedStatus))
                .build();
        when(userContextService.getCurrentUser()).thenReturn(currentUser);
        when(reportRepository.save(inputReport)).thenReturn(savedReport);

        Report result = reportService.createReport(inputReport);

        ArgumentCaptor<Report> reportCaptor = ArgumentCaptor.forClass(Report.class);

        // Verify that the report was saved with the correct data
        verify(reportRepository).save(reportCaptor.capture());
        Report capturedReport = reportCaptor.getValue();

        assertEquals("Test report", capturedReport.getTitle());
        assertEquals("Description", capturedReport.getDescription());
        assertEquals(1, capturedReport.getLocations().size());
        assertEquals(currentUser, capturedReport.getCreatedByUser());
        assertEquals(1, capturedReport.getStatuses().size());
        assertEquals(StatusType.NEW, capturedReport.getStatuses().getFirst().getStatusType());
        assertEquals(currentUser, capturedReport.getStatuses().getFirst().getSetByUser());

        assertEquals("Test report", result.getTitle());
        assertEquals("Description", result.getDescription());
        assertEquals(1, result.getLocations().size());
        assertEquals(createdOn, result.getCreatedOn());
        assertEquals(1, result.getStatuses().size());
        assertEquals(StatusType.NEW, result.getStatuses().getFirst().getStatusType());
    }

    @Test
    void testGetReportById_reportNotFound() {
        Long reportId = 1L;
        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());
        assertThrows(ReportNotFoundException.class,
                () -> reportService.getReportById(reportId));
    }

    @Test
    void testGetReportById_success() {
        Long reportId = 1L;
        Report foundReport = Report.builder()
                .id(reportId)
                .title("Found report")
                .build();
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(foundReport));
        Report result = reportService.getReportById(reportId);
        assertEquals(foundReport, result);
    }

    @Test
    void testGetReports_cityIdNotPassed() {
        List<Report> reports = List.of(
                Report.builder().id(1L).title("Report 1").build(),
                Report.builder().id(2L).title("Report 2").build()
        );
        when(reportRepository.findAll()).thenReturn(reports);
        List<Report> result = reportService.getReports(null);
        assertEquals(2, result.size());
        assertEquals(reports, result);
    }

    @Test
    void testGetReports_cityIdNotFound() {
        Long cityId = 1L;
        when(cityRepository.findById(cityId)).thenReturn(Optional.empty());
        assertThrows(CityNotFoundException.class,
                () -> reportService.getReports(cityId));
    }

    @Test
    void testGetReports_cityIdFound() {
        Long cityId = 1L;
        City city = City.builder()
                .id(cityId)
                .name("Test City")
                .centerPoint(cityCenterPoint)
                .radiusInMeters(25000L)
                .build();
        List<Report> reports = List.of(
                Report.builder().id(1L).title("Report 1").build(),
                Report.builder().id(2L).title("Report 2").build()
        );
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));
        when(cityCenterPoint.getX()).thenReturn(10.0);
        when(cityCenterPoint.getY()).thenReturn(20.0);
        when(reportRepository.findByCityId(10.0, 20.0, 25000L)).thenReturn(reports);

        List<Report> result = reportService.getReports(cityId);

        assertEquals(2, result.size());
        assertEquals(reports, result);
    }
}
