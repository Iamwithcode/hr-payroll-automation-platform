package com.company.authservice.dto;

import java.math.BigDecimal;

public class PayrollResponseDto {

    private Long id;
    private Long employeeId;
    private String payrollMonth;
    private BigDecimal basicSalary;
    private BigDecimal allowances;
    private BigDecimal deductions;
    private BigDecimal netSalary;
    private Integer workingDays;
    private Integer presentDays;
    private Integer leaveDays;
    private String generatedAt;
    private String status;

    public PayrollResponseDto(Long id, Long employeeId, String payrollMonth,
                              BigDecimal basicSalary, BigDecimal allowances,
                              BigDecimal deductions, BigDecimal netSalary,
                              Integer workingDays, Integer presentDays, Integer leaveDays,
                              String generatedAt, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.payrollMonth = payrollMonth;
        this.basicSalary = basicSalary;
        this.allowances = allowances;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.workingDays = workingDays;
        this.presentDays = presentDays;
        this.leaveDays = leaveDays;
        this.generatedAt = generatedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getPayrollMonth() {
        return payrollMonth;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public BigDecimal getAllowances() {
        return allowances;
    }

    public BigDecimal getDeductions() {
        return deductions;
    }

    public BigDecimal getNetSalary() {
        return netSalary;
    }

    public Integer getWorkingDays() {
        return workingDays;
    }

    public Integer getPresentDays() {
        return presentDays;
    }

    public Integer getLeaveDays() {
        return leaveDays;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public String getStatus() {
        return status;
    }
}