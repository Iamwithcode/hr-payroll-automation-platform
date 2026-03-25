package com.company.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureTestController {

    @GetMapping("/api/v1/secure/hello")
    public String secureHello() {
        return "You accessed a protected API!";
    }

    @GetMapping("/api/v1/admin/hello")
    public String adminHello() {
        return "Hello Admin!";
    }

    @GetMapping("/api/v1/hr/hello")
    public String hrHello() {
        return "Hello HR!";
    }

    @GetMapping("/api/v1/employee/hello")
    public String employeeHello() {
        return "Hello Employee!";
    }
}