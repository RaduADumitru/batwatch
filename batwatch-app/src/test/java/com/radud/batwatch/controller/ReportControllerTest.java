package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.ReportMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Location;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.model.Role;
import com.radud.batwatch.request.CreateLocationRequest;
import com.radud.batwatch.request.CreateReportRequest;
import com.radud.batwatch.response.LocationResponse;
import com.radud.batwatch.response.ReportResponse;
import com.radud.batwatch.response.StatusResponse;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static com.radud.batwatch.model.Role.USER;
import static com.radud.batwatch.model.StatusType.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @Mock
    private ReportMapper reportMapper;

    @InjectMocks
    private ReportController reportController;

    @Mock
    private Point point;

    @Test
    void testCreateReport() {
        CreateLocationRequest createLocationRequest = new CreateLocationRequest("Example location", 20.0, 30.0);
        CreateReportRequest request = new CreateReportRequest("Sample report", "Report for testing", List.of(createLocationRequest));
        Location location = Location.builder()
                .details("Example location")
                .locationPoint(point)
                .build();
        Report report = Report.builder()
                .title("Sample report")
                .description("Report for testing")
                .locations(List.of(location))
                .build();
        Instant createdOn = Instant.now();
        AppUser reportingUser = AppUser.builder()
                .id(1L)
                .username("reporter")
                .roles(Set.of(USER))
                .build();
        Report createdReport = Report.builder()
                .id(2L)
                .createdOn(createdOn)
                .title("Sample report")
                .description("Report for testing")
                .locations(List.of(location))
                .createdByUser(reportingUser)
                .build();
        LocationResponse locationResponse = new LocationResponse(
                3L,
                "Example location",
                30.0, 20.0);
        UserResponse reportingUserResponse = new UserResponse(1L, "reporter", Set.of(Role.USER));
        StatusResponse newStatusResponse = new StatusResponse(4L, createdOn, NEW, 2L, reportingUserResponse);
        ReportResponse response = new ReportResponse(
                2L,
                "Sample report",
                "Report for testing",
                createdOn,
                List.of(locationResponse),
                List.of(),
                List.of(newStatusResponse),
                List.of(),
                reportingUserResponse
        );

        when(reportMapper.toModel(request)).thenReturn(report);
        when(reportService.createReport(report)).thenReturn(createdReport);
        when(reportMapper.toResponse(createdReport)).thenReturn(response);

        ReportResponse actualResponse = reportController.createReport(request);

        assertEquals(2L, actualResponse.id());
        assertEquals("Sample report", actualResponse.title());
        assertEquals("Report for testing", actualResponse.description());
        assertEquals(createdOn, actualResponse.createdOn());
        assertEquals(1, actualResponse.locations().size());
        assertEquals(locationResponse, actualResponse.locations().getFirst());
        assertEquals(0, actualResponse.assignments().size());
        assertEquals(1, actualResponse.statuses().size());
        assertEquals(newStatusResponse, actualResponse.statuses().getFirst());
        assertEquals(0, actualResponse.comments().size());
        assertEquals(reportingUserResponse, actualResponse.createdByUser());

        verify(reportMapper).toModel(request);
        verify(reportService).createReport(report);
        verify(reportMapper).toResponse(createdReport);
        verifyNoMoreInteractions(reportService, reportMapper);
    }

    @Test
    void testGetReport() {
        Long reportId = 1L;
        Instant createdOn = Instant.now();
        AppUser reportingUser = AppUser.builder()
                .id(2L)
                .username("reporter")
                .roles(Set.of(USER))
                .build();
        Report report = Report.builder()
                .id(reportId)
                .createdOn(createdOn)
                .title("Existing report")
                .description("This report already exists")
                .createdByUser(reportingUser)
                .build();
        UserResponse reportingUserResponse = new UserResponse(2L, "reporter", Set.of(Role.USER));
        ReportResponse reportResponse = new ReportResponse(
                reportId,
                "Existing report",
                "This report already exists",
                createdOn,
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                reportingUserResponse
        );

        when(reportService.getReportById(reportId)).thenReturn(report);
        when(reportMapper.toResponse(report)).thenReturn(reportResponse);

        ReportResponse response = reportController.getReport(reportId);

        assertEquals(reportId, response.id());
        assertEquals("Existing report", response.title());
        assertEquals("This report already exists", response.description());
        assertEquals(createdOn, response.createdOn());
        assertEquals(0, response.locations().size());
        assertEquals(0, response.assignments().size());
        assertEquals(0, response.statuses().size());
        assertEquals(0, response.comments().size());
        assertEquals(reportingUserResponse, response.createdByUser());

        verify(reportService).getReportById(reportId);
        verify(reportMapper).toResponse(report);
        verifyNoMoreInteractions(reportService, reportMapper);
    }

    @Test
    void testGetReports_cityIdPassed() {
        Instant createdOn = Instant.now();
        AppUser reportingUser = AppUser.builder()
                .id(1L)
                .username("reporter")
                .roles(Set.of(USER))
                .build();
        Report report1 = Report.builder()
                .id(2L)
                .createdOn(createdOn)
                .title("Report 1")
                .description("First report")
                .createdByUser(reportingUser)
                .build();
        Report report2 = Report.builder()
                .id(3L)
                .createdOn(createdOn)
                .title("Report 2")
                .description("Second report")
                .createdByUser(reportingUser)
                .build();
        Long cityId = 4L;
        UserResponse reportingUserResponse = new UserResponse(1L, "reporter", Set.of(Role.USER));
        ReportResponse reportResponse1 = new ReportResponse(
                2L,
                "Report 1",
                "First report",
                createdOn,
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                reportingUserResponse
        );
        ReportResponse reportResponse2 = new ReportResponse(
                3L,
                "Report 2",
                "Second report",
                createdOn,
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                reportingUserResponse
        );
        List<Report> reports = List.of(report1, report2);
        List<ReportResponse> reportResponses = List.of(reportResponse1, reportResponse2);

        when(reportService.getReports(cityId)).thenReturn(reports);
        when(reportMapper.toResponseList(reports)).thenReturn(reportResponses);

        List<ReportResponse> responses = reportController.getReports(cityId);

        assertEquals(2, responses.size());
        assertEquals(reportResponse1, responses.get(0));
        assertEquals(reportResponse2, responses.get(1));

        verify(reportService).getReports(cityId);
        verify(reportMapper).toResponseList(reports);
        verifyNoMoreInteractions(reportService, reportMapper);
    }

    @Test
    void testGetReports_noCityId() {
        Instant createdOn = Instant.now();
        AppUser reportingUser = AppUser.builder()
                .id(1L)
                .username("reporter")
                .roles(Set.of(USER))
                .build();
        Report report1 = Report.builder()
                .id(2L)
                .createdOn(createdOn)
                .title("Report 1")
                .description("First report")
                .createdByUser(reportingUser)
                .build();
        Report report2 = Report.builder()
                .id(3L)
                .createdOn(createdOn)
                .title("Report 2")
                .description("Second report")
                .createdByUser(reportingUser)
                .build();
        UserResponse reportingUserResponse = new UserResponse(1L, "reporter", Set.of(Role.USER));
        ReportResponse reportResponse1 = new ReportResponse(
                2L,
                "Report 1",
                "First report",
                createdOn,
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                reportingUserResponse
        );
        ReportResponse reportResponse2 = new ReportResponse(
                3L,
                "Report 2",
                "Second report",
                createdOn,
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                reportingUserResponse
        );
        List<Report> reports = List.of(report1, report2);
        List<ReportResponse> reportResponses = List.of(reportResponse1, reportResponse2);

        when(reportService.getReports(null)).thenReturn(reports);
        when(reportMapper.toResponseList(reports)).thenReturn(reportResponses);

        List<ReportResponse> responses = reportController.getReports(null);

        assertEquals(2, responses.size());
        assertEquals(reportResponse1, responses.get(0));
        assertEquals(reportResponse2, responses.get(1));

        verify(reportService).getReports(null);
        verify(reportMapper).toResponseList(reports);
        verifyNoMoreInteractions(reportService, reportMapper);
    }
}
