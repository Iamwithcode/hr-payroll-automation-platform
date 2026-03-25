package com.company.authservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportSummaryResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long totalEmployees;
    private Long activeEmployees;
    private Long inactiveEmployees;
    private Long totalAttendanceRecords;
    private Long totalLeaveRequests;
    private Long pendingLeaves;
    private Long approvedLeaves;
    private Long rejectedLeaves;
    private Long totalPayrollRecords;
    private BigDecimal totalNetSalary;

    public ReportSummaryResponseDto(Long totalEmployees,
                                    Long activeEmployees,
                                    Long inactiveEmployees,
                                    Long totalAttendanceRecords,
                                    Long totalLeaveRequests,
                                    Long pendingLeaves,
                                    Long approvedLeaves,
                                    Long rejectedLeaves,
                                    Long totalPayrollRecords,
                                    BigDecimal totalNetSalary) {
        this.totalEmployees = totalEmployees;
        this.activeEmployees = activeEmployees;
        this.inactiveEmployees = inactiveEmployees;
        this.totalAttendanceRecords = totalAttendanceRecords;
        this.totalLeaveRequests = totalLeaveRequests;
        this.pendingLeaves = pendingLeaves;
        this.approvedLeaves = approvedLeaves;
        this.rejectedLeaves = rejectedLeaves;
        this.totalPayrollRecords = totalPayrollRecords;
        this.totalNetSalary = totalNetSalary;
    }

    public Long getTotalEmployees() {
        return totalEmployees;
    }

    public Long getActiveEmployees() {
        return activeEmployees;
    }

    public Long getInactiveEmployees() {
        return inactiveEmployees;
    }

    public Long getTotalAttendanceRecords() {
        return totalAttendanceRecords;
    }

    public Long getTotalLeaveRequests() {
        return totalLeaveRequests;
    }

    public Long getPendingLeaves() {
        return pendingLeaves;
    }

    public Long getApprovedLeaves() {
        return approvedLeaves;
    }

    public Long getRejectedLeaves() {
        return rejectedLeaves;
    }

    public Long getTotalPayrollRecords() {
        return totalPayrollRecords;
    }

    public BigDecimal getTotalNetSalary() {
        return totalNetSalary;
    }
}