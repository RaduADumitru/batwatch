package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.ReportMapper;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.request.CreateReportRequest;
import com.radud.batwatch.response.CityResponse;
import com.radud.batwatch.response.ReportResponse;
import com.radud.batwatch.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
@Tag(name = "Report", description = "Report management endpoints")
public class ReportController {

    private final ReportService reportService;

    private final ReportMapper reportMapper;

    @Operation(summary = "Create report", description = "Create a new report related to a bat sighting. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created report",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReportResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Request lacks valid authentication credentials",
                    content = @Content) })
    @PostMapping
    public ReportResponse createReport(@Valid @RequestBody CreateReportRequest request) {
        Report report = reportMapper.toModel(request);
        Report createdReport = reportService.createReport(report);
        return reportMapper.toResponse(createdReport);
    }

    @Operation(summary = "Get report", description = "Get a report by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found report",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReportResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid report id supplied",
                    content = @Content(mediaType = "text/plain", examples = { @ExampleObject(value = "Invalid value 'non-numeric' for parameter 'id'. Expected type: Long") })),
            @ApiResponse(responseCode = "404", description = "Report not found",
                    content = @Content(mediaType = "text/plain", examples = { @ExampleObject(value = "Report not found with id: 5432") })),
    })
    @GetMapping("/{id}")
    public ReportResponse getReport(@PathVariable Long id) {
        Report report = reportService.getReportById(id);
        return reportMapper.toResponse(report);
    }

    @Operation(summary = "Get reports", description = "Get all reports. Optionally filter by city - this will return reports of sightings inside that city.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtained reports",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReportResponse.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid city id supplied",
                    content = @Content(mediaType = "text/plain", examples = { @ExampleObject(value = "Invalid value 'non-numeric' for parameter 'id'. Expected type: Long") })),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(mediaType = "text/plain", examples = { @ExampleObject(value = "Invalid city id: 23245") }))
    })
    @GetMapping()
    public List<ReportResponse> getReports(@RequestParam(required = false) Long cityId) {
        List<Report> reports = reportService.getReports(cityId);
        return reportMapper.toResponseList(reports);
    }
}
