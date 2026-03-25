package com.company.authservice.controller;

import com.company.authservice.dto.PayrollRequestDto;
import com.company.authservice.dto.PayrollResponseDto;
import com.company.authservice.service.PayrollService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payroll")
@CrossOrigin(origins = "http://localhost:5173")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @PostMapping("/generate")
    public PayrollResponseDto generatePayroll(@RequestBody PayrollRequestDto requestDto) {
        return payrollService.generatePayroll(requestDto);
    }

    @GetMapping
    public List<PayrollResponseDto> getAllPayrollRecords() {
        return payrollService.getAllPayrollRecords();
    }

    @GetMapping("/employee/{employeeId}")
    public List<PayrollResponseDto> getPayrollByEmployeeId(@PathVariable Long employeeId) {
        return payrollService.getPayrollByEmployeeId(employeeId);
    }
}