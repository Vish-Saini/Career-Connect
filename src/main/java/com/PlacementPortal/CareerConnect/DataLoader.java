package com.PlacementPortal.CareerConnect;

import com.PlacementPortal.CareerConnect.Entity.Role;
import com.PlacementPortal.CareerConnect.Entity.User;
import com.PlacementPortal.CareerConnect.Repository.UserRepository;
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
        // Check if an admin user already exists
        if (userRepository.findByEmail("admin@portal.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setName("Admin");
            adminUser.setEmail("admin@portal.com");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Use a strong password in production!
            adminUser.setRole(Role.ROLE_ADMIN);
            userRepository.save(adminUser);
            System.out.println("Admin user created!");
        }
    }
}
