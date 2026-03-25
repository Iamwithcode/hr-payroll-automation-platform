package com.company.authservice.controller;

import com.company.authservice.dto.ReportSummaryResponseDto;
import com.company.authservice.service.ReportService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
//@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/summary")
    public ReportSummaryResponseDto getSummaryReport() {
        return reportService.getSummaryReport();
    }
}