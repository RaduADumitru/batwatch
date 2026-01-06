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
    @ResponseBody
    public ReportResponse createReport(@Valid @RequestBody CreateReportRequest request) {
        Report report = reportMapper.toModel(request);
        Report createdReport = reportService.createReport(report);
        return reportMapper.toResponse(createdReport);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ReportResponse getReport(@PathVariable Long id) {
        Report report = reportService.getReportById(id);
        return reportMapper.toResponse(report);
    }

    @GetMapping()
    @ResponseBody
    public List<ReportResponse> getReports(@RequestParam(required = false) Long cityId) {
        List<Report> reports = reportService.getReports(cityId);
        return reportMapper.toResponseList(reports);
    }
}
