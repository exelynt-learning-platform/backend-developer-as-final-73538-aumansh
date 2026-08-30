package com.example.bookingsystem.config;

import com.example.bookingsystem.model.Role;
import com.example.bookingsystem.model.User;
import com.example.bookingsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DataSeeder.class);

    @Override
    public void run(String... args) throws Exception {
        if ("admin123".equals(System.getenv().getOrDefault("SEED_ADMIN_PASSWORD", "admin123"))) {
            logger.warn("WARNING: Using default placeholder seed password for admin. Please change in production.");
        }
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(System.getenv().getOrDefault("SEED_ADMIN_PASSWORD", "admin123")));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode(System.getenv().getOrDefault("SEED_USER_PASSWORD", "user123")));
            user.setRole(Role.ROLE_USER);
            userRepository.save(user);
        }
    }
}
