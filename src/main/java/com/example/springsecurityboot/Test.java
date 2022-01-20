package com.example.springsecurityboot;

import com.example.springsecurityboot.models.Role;
import com.example.springsecurityboot.models.User;
import com.example.springsecurityboot.repositories.RoleRepository;
import com.example.springsecurityboot.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class Test {

    UserRepository userRepository;
    RoleRepository roleRepository;

    public Test(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void Runner() {

        Set<Role> adminSet = new HashSet<>();
        Set<Role> userSet = new HashSet<>();

        Role role = new Role("ADMIN");
        Role role2 = new Role("USER");

        adminSet.add(role);
        adminSet.add(role2);
        userSet.add(role2);

        roleRepository.save(role);
        roleRepository.save(role2);

        User user = new User();
        user.setUsername("Nadya");
        user.setLastname("Evseeva");
        user.setEmail("123@google.com");
        user.setAge((byte) 27);
        user.setPassword(new BCryptPasswordEncoder().encode("100"));
        user.setRoles(adminSet);

        User user2 = new User();
        user2.setUsername("Nastya");
        user2.setLastname("Korneeva");
        user2.setEmail("123@yahoo.com");
        user2.setAge((byte) 25);
        user2.setPassword(new BCryptPasswordEncoder().encode("100"));
        user2.setRoles(userSet);

        userRepository.save(user);
        userRepository.save(user2);
    }
}
