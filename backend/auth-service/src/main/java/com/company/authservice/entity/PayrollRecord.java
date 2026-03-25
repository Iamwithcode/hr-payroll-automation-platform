package com.company.authservice.entity;

import com.company.authservice.enums.PayrollStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PayrollRecord {

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
    private LocalDateTime generatedAt;
    private PayrollStatus status;

    public PayrollRecord() {
    }

    public PayrollRecord(Long id, Long employeeId, String payrollMonth, BigDecimal basicSalary,
                         BigDecimal allowances, BigDecimal deductions, BigDecimal netSalary,
                         Integer workingDays, Integer presentDays, Integer leaveDays,
                         LocalDateTime generatedAt, PayrollStatus status) {
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

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public PayrollStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setPayrollMonth(String payrollMonth) {
        this.payrollMonth = payrollMonth;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public void setAllowances(BigDecimal allowances) {
        this.allowances = allowances;
    }

    public void setDeductions(BigDecimal deductions) {
        this.deductions = deductions;
    }

    public void setNetSalary(BigDecimal netSalary) {
        this.netSalary = netSalary;
    }

    public void setWorkingDays(Integer workingDays) {
        this.workingDays = workingDays;
    }

    public void setPresentDays(Integer presentDays) {
        this.presentDays = presentDays;
    }

    public void setLeaveDays(Integer leaveDays) {
        this.leaveDays = leaveDays;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public void setStatus(PayrollStatus status) {
        this.status = status;
    }
}