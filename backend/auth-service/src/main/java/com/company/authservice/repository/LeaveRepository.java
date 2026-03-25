package com.company.authservice.repository;

import com.company.authservice.entity.LeaveRequest;
import com.company.authservice.enums.LeaveStatus;
import com.company.authservice.enums.LeaveType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LeaveRepository {

    private final List<LeaveRequest> leaveRequests = new ArrayList<>();

    public LeaveRepository() {
        leaveRequests.add(new LeaveRequest(
                1L,
                2L,
                LeaveType.CASUAL,
                LocalDate.of(2026, 3, 28),
                LocalDate.of(2026, 3, 29),
                2,
                "Personal work",
                LeaveStatus.PENDING,
                null,
                LocalDateTime.now(),
                null
        ));
    }

    public List<LeaveRequest> findAll() {
        return leaveRequests;
    }

    public List<LeaveRequest> findByEmployeeId(Long employeeId) {
        return leaveRequests.stream()
                .filter(request -> request.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }

    public Optional<LeaveRequest> findById(Long id) {
        return leaveRequests.stream()
                .filter(request -> request.getId().equals(id))
                .findFirst();
    }

    public LeaveRequest save(LeaveRequest leaveRequest) {
        if (leaveRequest.getId() == null) {
            long nextId = leaveRequests.stream()
                    .mapToLong(LeaveRequest::getId)
                    .max()
                    .orElse(0L) + 1;
            leaveRequest.setId(nextId);
            leaveRequests.add(leaveRequest);
        } else {
            leaveRequests.removeIf(existing -> existing.getId().equals(leaveRequest.getId()));
            leaveRequests.add(leaveRequest);
        }
        return leaveRequest;
    }
}