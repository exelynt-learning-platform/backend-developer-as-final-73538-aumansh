package com.example.bookingsystem.config;

import com.example.bookingsystem.model.User;
import com.example.bookingsystem.model.Role;
import com.example.bookingsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@lombok.extern.slf4j.Slf4j
public class DataSeeder implements CommandLineRunner {

    @org.springframework.beans.factory.annotation.Value("${SEED_ADMIN_PASSWORD}")
    private String adminPass;

    @org.springframework.beans.factory.annotation.Value("${SEED_USER_PASSWORD}")
    private String userPass;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    

    @Override
    public void run(String... args) throws Exception {
                if (adminPass == null || adminPass.isEmpty()) {
            throw new RuntimeException("SEED_ADMIN_PASSWORD environment variable is missing.");
        }
                if (userPass == null || userPass.isEmpty()) {
            throw new RuntimeException("SEED_USER_PASSWORD environment variable is missing.");
        }
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(adminPass));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode(userPass));
            user.setRole(Role.ROLE_USER);
            userRepository.save(user);
        }
    }
}
