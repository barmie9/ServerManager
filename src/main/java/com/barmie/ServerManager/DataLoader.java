package com.barmie.ServerManager;

import com.barmie.ServerManager.model.User;
import com.barmie.ServerManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ROLE_admin");
        userRepository.save(admin);

        User superAdmin = new User();
        superAdmin.setUsername("superAdmin");
        superAdmin.setPassword(passwordEncoder.encode("superAdmin123"));
        superAdmin.setRole("ROLE_superAdmin");
        userRepository.save(superAdmin);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setRole("ROLE_user");
        userRepository.save(user);
    }
}