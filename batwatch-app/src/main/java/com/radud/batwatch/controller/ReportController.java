package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.ReportMapper;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.request.CreateReportRequest;
import com.radud.batwatch.response.ReportResponse;
import com.radud.batwatch.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    private final ReportMapper reportMapper;

    public ReportController(ReportService reportService, ReportMapper reportMapper) {
        this.reportService = reportService;
        this.reportMapper = reportMapper;
    }

    @PostMapping
    public ResponseEntity<?> createReport(@Valid @RequestBody CreateReportRequest request) {
        Report report = reportMapper.toModel(request);
        Report createdReport = reportService.createReport(report);
        ReportResponse response = reportMapper.toResponse(createdReport);
        return ResponseEntity.ok(response);
    }


}
