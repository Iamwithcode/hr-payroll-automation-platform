package com.company.authservice.repository;

import com.company.authservice.entity.Role;
import com.company.authservice.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        Role adminRole = new Role(1L, "ADMIN");
        Role hrRole = new Role(2L, "HR");
        Role employeeRole = new Role(3L, "EMPLOYEE");

        users.add(new User(1L, "admin", "admin@mail.com", "admin123", adminRole));
        users.add(new User(2L, "hruser", "hr@mail.com", "hr123", hrRole));
        users.add(new User(3L, "empuser", "emp@mail.com", "emp123", employeeRole));
    }

    public List<User> findAll() {
        return users;
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}