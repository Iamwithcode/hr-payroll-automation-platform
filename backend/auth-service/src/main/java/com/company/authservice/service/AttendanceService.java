package com.company.authservice.service;

import com.company.authservice.dto.AttendanceRequest;
import com.company.authservice.dto.AttendanceResponse;
import com.company.authservice.entity.Attendance;
import com.company.authservice.enums.AttendanceStatus;
import com.company.authservice.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceResponse markAttendance(AttendanceRequest request) {
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(request.getEmployeeId());
        attendance.setAttendanceDate(LocalDate.parse(request.getAttendanceDate()));
        attendance.setCheckInTime(
                request.getCheckInTime() != null && !request.getCheckInTime().isBlank()
                        ? LocalDateTime.parse(request.getCheckInTime())
                        : null
        );
        attendance.setCheckOutTime(
                request.getCheckOutTime() != null && !request.getCheckOutTime().isBlank()
                        ? LocalDateTime.parse(request.getCheckOutTime())
                        : null
        );
        attendance.setStatus(AttendanceStatus.valueOf(request.getStatus().toUpperCase()));
        attendance.setRemarks(request.getRemarks());

        Attendance saved = attendanceRepository.save(attendance);
        return mapToResponse(saved);
    }

    public List<AttendanceResponse> getAllAttendance() {
        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<AttendanceResponse> getAttendanceByEmployeeId(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AttendanceResponse mapToResponse(Attendance attendance) {
        return new AttendanceResponse(
                attendance.getId(),
                attendance.getEmployeeId(),
                attendance.getAttendanceDate() != null ? attendance.getAttendanceDate().toString() : null,
                attendance.getCheckInTime() != null ? attendance.getCheckInTime().toString() : null,
                attendance.getCheckOutTime() != null ? attendance.getCheckOutTime().toString() : null,
                attendance.getStatus() != null ? attendance.getStatus().name() : null,
                attendance.getRemarks()
        );
    }
}