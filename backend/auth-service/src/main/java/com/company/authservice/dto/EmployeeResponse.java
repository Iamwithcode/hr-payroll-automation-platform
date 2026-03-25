package com.company.authservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeResponse {

    private Long id;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private String designation;
    private LocalDate dateOfJoining;
    private BigDecimal basicSalary;
    private String status;

    public EmployeeResponse(Long id, String employeeCode, String firstName, String lastName, String email,
                            String phone, String department, String designation, LocalDate dateOfJoining,
                            BigDecimal basicSalary, String status) {
        this.id = id;
        this.employeeCode = employeeCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.designation = designation;
        this.dateOfJoining = dateOfJoining;
        this.basicSalary = basicSalary;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public String getStatus() {
        return status;
    }
}