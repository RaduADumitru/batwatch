package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.ReportMapper;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.request.CreateReportRequest;
import com.radud.batwatch.response.ReportResponse;
import com.radud.batwatch.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping
    public ReportResponse createReport(@Valid @RequestBody CreateReportRequest request) {
        Report report = reportMapper.toModel(request);
        Report createdReport = reportService.createReport(report);
        return reportMapper.toResponse(createdReport);
    }

    @Operation(summary = "Get report", description = "Get a report by its id.")
    @GetMapping("/{id}")
    public ReportResponse getReport(@PathVariable Long id) {
        Report report = reportService.getReportById(id);
        return reportMapper.toResponse(report);
    }

    @Operation(summary = "Get reports", description = "Get all reports. Optionally filter by city - this will return reports of sightings inside that city.")
    @GetMapping()
    public List<ReportResponse> getReports(@RequestParam(required = false) Long cityId) {
        List<Report> reports = reportService.getReports(cityId);
        return reportMapper.toResponseList(reports);
    }
}
