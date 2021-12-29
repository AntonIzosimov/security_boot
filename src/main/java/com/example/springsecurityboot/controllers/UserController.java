package com.example.springsecurityboot.controllers;


import com.example.springsecurityboot.models.Role;
import com.example.springsecurityboot.models.User;
import com.example.springsecurityboot.repositories.RoleRepository;
import com.example.springsecurityboot.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/admin")
    public String getAll(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/user-create")
    public String createUserForm(Model model) {
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("user", new User());
        model.addAttribute("roleList", roleList);
        return "user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUser(@ModelAttribute("user") User user) {
        String pass = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(pass));
        userRepository.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("user-delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user-edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        List<Role> roleList = roleRepository.findAll();
        User user = userRepository.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleList);
        return "/user-edit";
    }
    @PatchMapping("/admin/user-edit/{id}")
    public String edit(@ModelAttribute("user") User user) {
        String pass = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(pass));
        userRepository.save(user);
        return "redirect:/admin";
    }

//    private Set<Role> addRolesToUser(String[] roles) {
//        List<Role> roleList = roleRepository.findAll();
//        Set<Role> userRoleSet = new HashSet<>();
//
//        for (Role role : roleList) {
//            for (int i = 0; i < roles.length; i++) {
//                if(roles[i].contains(role.getName())){
//                    userRoleSet.add(role);
//                }
//            }
//        }
//        return userRoleSet;
//    }

    @RequestMapping("/user")
    public String profile(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/user";
    }
}
