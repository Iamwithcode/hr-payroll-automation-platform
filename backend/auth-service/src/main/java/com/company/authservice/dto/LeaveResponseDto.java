package com.company.authservice.dto;

public class LeaveResponseDto {

    private Long id;
    private Long employeeId;
    private String leaveType;
    private String startDate;
    private String endDate;
    private Integer totalDays;
    private String reason;
    private String status;
    private Long approvedBy;
    private String appliedAt;
    private String actionAt;

    public LeaveResponseDto(Long id, Long employeeId, String leaveType, String startDate,
                            String endDate, Integer totalDays, String reason, String status,
                            Long approvedBy, String appliedAt, String actionAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDays = totalDays;
        this.reason = reason;
        this.status = status;
        this.approvedBy = approvedBy;
        this.appliedAt = appliedAt;
        this.actionAt = actionAt;
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public String getAppliedAt() {
        return appliedAt;
    }

    public String getActionAt() {
        return actionAt;
    }
}