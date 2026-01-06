package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.ReportMapper;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.request.CreateReportRequest;
import com.radud.batwatch.response.ReportResponse;
import com.radud.batwatch.service.ReportService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    private final ReportMapper reportMapper;

    @PostMapping
    public ResponseEntity<?> createReport(@Valid @RequestBody CreateReportRequest request) {
        Report report = reportMapper.toModel(request);
        Report createdReport = reportService.createReport(report);
        ReportResponse response = reportMapper.toResponse(createdReport);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {
        Report report = reportService.getReportById(id);
        ReportResponse response = reportMapper.toResponse(report);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<?> getReports(@RequestParam(required = false) Long cityId) {
        List<Report> reports = reportService.getReports(cityId);
        List<ReportResponse> response = reportMapper.toResponseList(reports);
        return ResponseEntity.ok(response);
    }


}
