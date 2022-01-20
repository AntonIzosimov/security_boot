package com.example.springsecurityboot.controllers;

import com.example.springsecurityboot.models.Role;
import com.example.springsecurityboot.models.User;
import com.example.springsecurityboot.service.RoleService;
import com.example.springsecurityboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/admin")
    public String getAll(Model model, Principal principal) {
        List<User> users = userService.findAll();
        List<Role> roles = roleService.findAll();
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("users", users);
        model.addAttribute("roleList", roles);
        model.addAttribute("user", user);
        return "admin";
    }

    @GetMapping("/user")
    public String profile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/user";
    }
}
