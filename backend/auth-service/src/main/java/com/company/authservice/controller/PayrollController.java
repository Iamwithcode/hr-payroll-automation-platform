package com.company.authservice.controller;

import com.company.authservice.dto.PayrollRequestDto;
import com.company.authservice.dto.PayrollResponseDto;
import com.company.authservice.service.PayrollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payroll")
@CrossOrigin(origins = "http://localhost:5173")
public class PayrollController {

    private static final Logger logger = LoggerFactory.getLogger(PayrollController.class);

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @PostMapping("/generate")
    public PayrollResponseDto generatePayroll(@RequestBody PayrollRequestDto requestDto) {
        logger.info("Received payroll generation request for employee id: {}", requestDto.getEmployeeId());
        return payrollService.generatePayroll(requestDto);
    }

    @GetMapping
    public List<PayrollResponseDto> getAllPayrollRecords() {
        logger.info("Fetching all payroll records");
        return payrollService.getAllPayrollRecords();
    }

    @GetMapping("/employee/{employeeId}")
    public List<PayrollResponseDto> getPayrollByEmployeeId(@PathVariable Long employeeId) {
        logger.info("Fetching payroll records for employee id: {}", employeeId);
        return payrollService.getPayrollByEmployeeId(employeeId);
    }
}