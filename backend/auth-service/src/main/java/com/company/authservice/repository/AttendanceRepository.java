package com.company.authservice.repository;

import com.company.authservice.entity.Attendance;
import com.company.authservice.enums.AttendanceStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AttendanceRepository {

    private final List<Attendance> attendanceRecords = new ArrayList<>();

    public AttendanceRepository() {
        attendanceRecords.add(new Attendance(
                1L,
                1L,
                LocalDate.of(2026, 3, 25),
                LocalDateTime.of(2026, 3, 25, 9, 0),
                LocalDateTime.of(2026, 3, 25, 18, 0),
                AttendanceStatus.PRESENT,
                "On time"
        ));
    }

    public List<Attendance> findAll() {
        return attendanceRecords;
    }

    public List<Attendance> findByEmployeeId(Long employeeId) {
        return attendanceRecords.stream()
                .filter(record -> record.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }

    public Attendance save(Attendance attendance) {
        if (attendance.getId() == null) {
            long nextId = attendanceRecords.stream()
                    .mapToLong(Attendance::getId)
                    .max()
                    .orElse(0L) + 1;
            attendance.setId(nextId);
        } else {
            attendanceRecords.removeIf(existing -> existing.getId().equals(attendance.getId()));
        }

        attendanceRecords.add(attendance);
        return attendance;
    }
}