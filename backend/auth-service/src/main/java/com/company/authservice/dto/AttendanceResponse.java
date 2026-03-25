package com.company.authservice.dto;

public class AttendanceResponse {

    private Long id;
    private Long employeeId;
    private String attendanceDate;
    private String checkInTime;
    private String checkOutTime;
    private String status;
    private String remarks;

    public AttendanceResponse(Long id, Long employeeId, String attendanceDate,
                              String checkInTime, String checkOutTime,
                              String status, String remarks) {
        this.id = id;
        this.employeeId = employeeId;
        this.attendanceDate = attendanceDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.status = status;
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public String getStatus() {
        return status;
    }

    public String getRemarks() {
        return remarks;
    }
}