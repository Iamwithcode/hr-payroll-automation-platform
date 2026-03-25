package com.company.authservice.controller;

import com.company.authservice.dto.LeaveRequestDto;
import com.company.authservice.dto.LeaveResponseDto;
import com.company.authservice.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leaves")
@CrossOrigin(origins = "http://localhost:5173")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    public LeaveResponseDto applyLeave(@RequestBody LeaveRequestDto requestDto) {
        return leaveService.applyLeave(requestDto);
    }

    @GetMapping
    public List<LeaveResponseDto> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveResponseDto> getLeavesByEmployee(@PathVariable Long employeeId) {
        return leaveService.getLeavesByEmployeeId(employeeId);
    }

    @PutMapping("/{id}/approve")
    public LeaveResponseDto approveLeave(@PathVariable Long id,
                                         @RequestParam Long approvedBy) {
        return leaveService.approveLeave(id, approvedBy);
    }

    @PutMapping("/{id}/reject")
    public LeaveResponseDto rejectLeave(@PathVariable Long id,
                                        @RequestParam Long approvedBy) {
        return leaveService.rejectLeave(id, approvedBy);
    }
}