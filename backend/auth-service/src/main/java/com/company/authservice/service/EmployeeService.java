package com.company.authservice.service;

import com.company.authservice.dto.EmployeeRequest;
import com.company.authservice.dto.EmployeeResponse;
import com.company.authservice.entity.Employee;
import com.company.authservice.enums.EmployeeStatus;
import com.company.authservice.exception.ResourceNotFoundException;
import com.company.authservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        return mapToResponse(employee);
    }

    @CacheEvict(value = "reportSummary", allEntries = true)
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        logger.info("Creating employee with code: {}", request.getEmployeeCode());
        Employee employee = new Employee();
        mapRequestToEmployee(request, employee);

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToResponse(savedEmployee);
    }

    @CacheEvict(value = "reportSummary", allEntries = true)
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        logger.info("Updating employee with id: {}", id);
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        mapRequestToEmployee(request, existingEmployee);

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return mapToResponse(updatedEmployee);
    }

    private void mapRequestToEmployee(EmployeeRequest request, Employee employee) {
        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setDateOfJoining(request.getDateOfJoining());
        employee.setBasicSalary(request.getBasicSalary());
        employee.setStatus(EmployeeStatus.valueOf(request.getStatus().toUpperCase()));
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getDepartment(),
                employee.getDesignation(),
                employee.getDateOfJoining(),
                employee.getBasicSalary(),
                employee.getStatus().name()
        );
    }
}