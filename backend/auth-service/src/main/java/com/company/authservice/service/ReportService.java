package com.company.authservice.service;

import com.company.authservice.dto.ReportSummaryResponseDto;
import com.company.authservice.entity.Employee;
import com.company.authservice.entity.LeaveRequest;
import com.company.authservice.entity.PayrollRecord;
import com.company.authservice.enums.EmployeeStatus;
import com.company.authservice.enums.LeaveStatus;
import com.company.authservice.repository.AttendanceRepository;
import com.company.authservice.repository.EmployeeRepository;
import com.company.authservice.repository.LeaveRepository;
import com.company.authservice.repository.PayrollRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReportService {

        private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRepository leaveRepository;
    private final PayrollRepository payrollRepository;

    public ReportService(EmployeeRepository employeeRepository,
                         AttendanceRepository attendanceRepository,
                         LeaveRepository leaveRepository,
                         PayrollRepository payrollRepository) {
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.leaveRepository = leaveRepository;
        this.payrollRepository = payrollRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    @Cacheable("reportSummary")
    public ReportSummaryResponseDto getSummaryReport() {

        logger.info("Generating fresh report summary from repositories");

        List<Employee> employees = employeeRepository.findAll();
        List<LeaveRequest> leaves = leaveRepository.findAll();
        List<PayrollRecord> payrollRecords = payrollRepository.findAll();

        long totalEmployees = employees.size();
        long activeEmployees = employees.stream()
                .filter(employee -> employee.getStatus() == EmployeeStatus.ACTIVE)
                .count();
        long inactiveEmployees = employees.stream()
                .filter(employee -> employee.getStatus() == EmployeeStatus.INACTIVE)
                .count();

        long totalAttendanceRecords = attendanceRepository.findAll().size();

        long totalLeaveRequests = leaves.size();
        long pendingLeaves = leaves.stream()
                .filter(leave -> leave.getStatus() == LeaveStatus.PENDING)
                .count();
        long approvedLeaves = leaves.stream()
                .filter(leave -> leave.getStatus() == LeaveStatus.APPROVED)
                .count();
        long rejectedLeaves = leaves.stream()
                .filter(leave -> leave.getStatus() == LeaveStatus.REJECTED)
                .count();

        long totalPayrollRecords = payrollRecords.size();
        BigDecimal totalNetSalary = payrollRecords.stream()
                .map(PayrollRecord::getNetSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ReportSummaryResponseDto(
                totalEmployees,
                activeEmployees,
                inactiveEmployees,
                totalAttendanceRecords,
                totalLeaveRequests,
                pendingLeaves,
                approvedLeaves,
                rejectedLeaves,
                totalPayrollRecords,
                totalNetSalary
        );
    }
}