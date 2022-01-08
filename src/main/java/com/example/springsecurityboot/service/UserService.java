package com.example.springsecurityboot.service;

import com.example.springsecurityboot.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void save(User user);
    void deleteById(Long id);
    User findUserById(Long id);
    User findByUsername(String username);
}
