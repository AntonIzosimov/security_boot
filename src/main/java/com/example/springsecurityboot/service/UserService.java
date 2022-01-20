package com.example.springsecurityboot.service;

import com.example.springsecurityboot.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void save(User user);
    User findUserById(Long id);
    User findByUsername(String username);
    void delete(User user);
}
