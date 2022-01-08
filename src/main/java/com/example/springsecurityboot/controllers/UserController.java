package com.example.springsecurityboot.controllers;


import com.example.springsecurityboot.models.Role;
import com.example.springsecurityboot.models.User;
import com.example.springsecurityboot.service.RoleService;
import com.example.springsecurityboot.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
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

    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam("roles") Long id) {
        user.setRoles(addRolesToUser(id));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PatchMapping("/edit/{id}")
    public String edit(@ModelAttribute User user, @RequestParam("roles") Long id) {
        user.setRoles(addRolesToUser(id));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    private Set<Role> addRolesToUser(Long id) {
        Role role = roleService.findRoleById(id);
        Set<Role> userRoleSet = new HashSet<>();
        userRoleSet.add(role);
        return userRoleSet;
    }

    @GetMapping("/user")
    public String profile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/user";
    }
}
