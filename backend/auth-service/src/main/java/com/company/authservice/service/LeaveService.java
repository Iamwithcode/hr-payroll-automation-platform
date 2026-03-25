package com.company.authservice.service;

import com.company.authservice.dto.LeaveRequestDto;
import com.company.authservice.dto.LeaveResponseDto;
import com.company.authservice.entity.LeaveRequest;
import com.company.authservice.enums.LeaveStatus;
import com.company.authservice.enums.LeaveType;
import com.company.authservice.exception.ResourceNotFoundException;
import com.company.authservice.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public LeaveResponseDto applyLeave(LeaveRequestDto requestDto) {
        LocalDate startDate = LocalDate.parse(requestDto.getStartDate());
        LocalDate endDate = LocalDate.parse(requestDto.getEndDate());

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(requestDto.getEmployeeId());
        leaveRequest.setLeaveType(LeaveType.valueOf(requestDto.getLeaveType().toUpperCase()));
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setTotalDays(totalDays);
        leaveRequest.setReason(requestDto.getReason());
        leaveRequest.setStatus(LeaveStatus.PENDING);
        leaveRequest.setAppliedAt(LocalDateTime.now());

        LeaveRequest saved = leaveRepository.save(leaveRequest);
        return mapToResponse(saved);
    }

    public List<LeaveResponseDto> getAllLeaves() {
        return leaveRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<LeaveResponseDto> getLeavesByEmployeeId(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public LeaveResponseDto approveLeave(Long id, Long approvedBy) {
        LeaveRequest leaveRequest = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + id));

        leaveRequest.setStatus(LeaveStatus.APPROVED);
        leaveRequest.setApprovedBy(approvedBy);
        leaveRequest.setActionAt(LocalDateTime.now());

        LeaveRequest updated = leaveRepository.save(leaveRequest);
        return mapToResponse(updated);
    }

    public LeaveResponseDto rejectLeave(Long id, Long approvedBy) {
        LeaveRequest leaveRequest = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + id));

        leaveRequest.setStatus(LeaveStatus.REJECTED);
        leaveRequest.setApprovedBy(approvedBy);
        leaveRequest.setActionAt(LocalDateTime.now());

        LeaveRequest updated = leaveRepository.save(leaveRequest);
        return mapToResponse(updated);
    }

    private LeaveResponseDto mapToResponse(LeaveRequest leaveRequest) {
        return new LeaveResponseDto(
                leaveRequest.getId(),
                leaveRequest.getEmployeeId(),
                leaveRequest.getLeaveType().name(),
                leaveRequest.getStartDate().toString(),
                leaveRequest.getEndDate().toString(),
                leaveRequest.getTotalDays(),
                leaveRequest.getReason(),
                leaveRequest.getStatus().name(),
                leaveRequest.getApprovedBy(),
                leaveRequest.getAppliedAt() != null ? leaveRequest.getAppliedAt().toString() : null,
                leaveRequest.getActionAt() != null ? leaveRequest.getActionAt().toString() : null
        );
    }
}