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
        Role role = new Role(1L, "ADMIN");
        users.add(new User(1L, "admin", "admin@mail.com", "password", role));
    }

    public List<User> findAll() {
        return users;
    }
}