package com.company.authservice.repository;

import com.company.authservice.entity.PayrollRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PayrollRepository {

    private final List<PayrollRecord> payrollRecords = new ArrayList<>();

    public List<PayrollRecord> findAll() {
        return payrollRecords;
    }

    public List<PayrollRecord> findByEmployeeId(Long employeeId) {
        return payrollRecords.stream()
                .filter(record -> record.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }

    public Optional<PayrollRecord> findByEmployeeIdAndPayrollMonth(Long employeeId, String payrollMonth) {
        return payrollRecords.stream()
                .filter(record ->
                        record.getEmployeeId().equals(employeeId)
                                && record.getPayrollMonth().equalsIgnoreCase(payrollMonth))
                .findFirst();
    }

    public PayrollRecord save(PayrollRecord payrollRecord) {
        if (payrollRecord.getId() == null) {
            long nextId = payrollRecords.stream()
                    .mapToLong(PayrollRecord::getId)
                    .max()
                    .orElse(0L) + 1;
            payrollRecord.setId(nextId);
            payrollRecords.add(payrollRecord);
        } else {
            payrollRecords.removeIf(existing -> existing.getId().equals(payrollRecord.getId()));
            payrollRecords.add(payrollRecord);
        }

        return payrollRecord;
    }
}