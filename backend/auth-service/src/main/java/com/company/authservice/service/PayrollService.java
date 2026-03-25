package com.company.authservice.service;

import com.company.authservice.dto.PayrollRequestDto;
import com.company.authservice.dto.PayrollResponseDto;
import com.company.authservice.entity.PayrollRecord;
import com.company.authservice.enums.PayrollStatus;
import com.company.authservice.repository.PayrollRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PayrollService {

    private final PayrollRepository payrollRepository;

    public PayrollService(PayrollRepository payrollRepository) {
        this.payrollRepository = payrollRepository;
    }

    public PayrollResponseDto generatePayroll(PayrollRequestDto requestDto) {
        validatePayrollRequest(requestDto);

        int absentDays = requestDto.getWorkingDays() - (requestDto.getPresentDays() + requestDto.getLeaveDays());
        if (absentDays < 0) {
            absentDays = 0;
        }

        BigDecimal perDaySalary = requestDto.getBasicSalary()
                .divide(BigDecimal.valueOf(requestDto.getWorkingDays()), 2, RoundingMode.HALF_UP);

        BigDecimal absenceDeduction = perDaySalary.multiply(BigDecimal.valueOf(absentDays));

        BigDecimal netSalary = requestDto.getBasicSalary()
                .add(requestDto.getAllowances())
                .subtract(requestDto.getDeductions())
                .subtract(absenceDeduction);

        if (netSalary.compareTo(BigDecimal.ZERO) < 0) {
            netSalary = BigDecimal.ZERO;
        }

        PayrollRecord payrollRecord = payrollRepository
                .findByEmployeeIdAndPayrollMonth(requestDto.getEmployeeId(), requestDto.getPayrollMonth())
                .orElse(new PayrollRecord());

        payrollRecord.setEmployeeId(requestDto.getEmployeeId());
        payrollRecord.setPayrollMonth(requestDto.getPayrollMonth());
        payrollRecord.setBasicSalary(requestDto.getBasicSalary());
        payrollRecord.setAllowances(requestDto.getAllowances());
        payrollRecord.setDeductions(requestDto.getDeductions().add(absenceDeduction));
        payrollRecord.setNetSalary(netSalary);
        payrollRecord.setWorkingDays(requestDto.getWorkingDays());
        payrollRecord.setPresentDays(requestDto.getPresentDays());
        payrollRecord.setLeaveDays(requestDto.getLeaveDays());
        payrollRecord.setGeneratedAt(LocalDateTime.now());
        payrollRecord.setStatus(PayrollStatus.GENERATED);

        PayrollRecord saved = payrollRepository.save(payrollRecord);
        return mapToResponse(saved);
    }

    public List<PayrollResponseDto> getAllPayrollRecords() {
        return payrollRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<PayrollResponseDto> getPayrollByEmployeeId(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void validatePayrollRequest(PayrollRequestDto requestDto) {
        if (requestDto.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee id is required");
        }
        if (requestDto.getPayrollMonth() == null || requestDto.getPayrollMonth().isBlank()) {
            throw new IllegalArgumentException("Payroll month is required");
        }
        if (requestDto.getBasicSalary() == null || requestDto.getBasicSalary().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Basic salary must be valid");
        }
        if (requestDto.getAllowances() == null) {
            requestDto.setAllowances(BigDecimal.ZERO);
        }
        if (requestDto.getDeductions() == null) {
            requestDto.setDeductions(BigDecimal.ZERO);
        }
        if (requestDto.getWorkingDays() == null || requestDto.getWorkingDays() <= 0) {
            throw new IllegalArgumentException("Working days must be greater than zero");
        }
        if (requestDto.getPresentDays() == null || requestDto.getPresentDays() < 0) {
            throw new IllegalArgumentException("Present days must be valid");
        }
        if (requestDto.getLeaveDays() == null || requestDto.getLeaveDays() < 0) {
            throw new IllegalArgumentException("Leave days must be valid");
        }
    }

    private PayrollResponseDto mapToResponse(PayrollRecord payrollRecord) {
        return new PayrollResponseDto(
                payrollRecord.getId(),
                payrollRecord.getEmployeeId(),
                payrollRecord.getPayrollMonth(),
                payrollRecord.getBasicSalary(),
                payrollRecord.getAllowances(),
                payrollRecord.getDeductions(),
                payrollRecord.getNetSalary(),
                payrollRecord.getWorkingDays(),
                payrollRecord.getPresentDays(),
                payrollRecord.getLeaveDays(),
                payrollRecord.getGeneratedAt() != null ? payrollRecord.getGeneratedAt().toString() : null,
                payrollRecord.getStatus() != null ? payrollRecord.getStatus().name() : null
        );
    }
}