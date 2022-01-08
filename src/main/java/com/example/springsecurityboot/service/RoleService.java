package com.example.springsecurityboot.service;

import com.example.springsecurityboot.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findRoleById(Long id);
}
