package com.company.authservice.repository;

import com.company.authservice.entity.Employee;
import com.company.authservice.enums.EmployeeStatus;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(
                1L,
                "EMP001",
                "Rahul",
                "Kumar",
                "rahul@company.com",
                "9876543210",
                "Engineering",
                "Software Engineer",
                LocalDate.of(2025, 1, 10),
                new BigDecimal("50000"),
                EmployeeStatus.ACTIVE
        ));

        employees.add(new Employee(
                2L,
                "EMP002",
                "Priya",
                "Sharma",
                "priya@company.com",
                "9876543211",
                "HR",
                "HR Executive",
                LocalDate.of(2024, 11, 5),
                new BigDecimal("45000"),
                EmployeeStatus.ACTIVE
        ));
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Optional<Employee> findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst();
    }

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            long nextId = employees.stream()
                    .mapToLong(Employee::getId)
                    .max()
                    .orElse(0L) + 1;
            employee.setId(nextId);
            employees.add(employee);
        } else {
            employees.removeIf(existing -> existing.getId().equals(employee.getId()));
            employees.add(employee);
        }
        return employee;
    }
}