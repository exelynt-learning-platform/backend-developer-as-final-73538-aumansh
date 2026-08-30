package com.example.bookingsystem.config;

import com.example.bookingsystem.model.User;
import com.example.bookingsystem.model.Role;
import com.example.bookingsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
@lombok.extern.slf4j.Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder, Environment env) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminPass = env.getProperty("SEED_ADMIN_PASSWORD");
        if (adminPass == null || adminPass.isEmpty()) {
            adminPass = java.util.UUID.randomUUID().toString();
            log.warn("SEED_ADMIN_PASSWORD not set! Generated random admin password: {}", adminPass);
        }
        String userPass = env.getProperty("SEED_USER_PASSWORD");
        if (userPass == null || userPass.isEmpty()) {
            userPass = java.util.UUID.randomUUID().toString();
            log.warn("SEED_USER_PASSWORD not set! Generated random user password: {}", userPass);
        }
        
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(adminPass));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
        }
        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode(userPass));
            user.setRole(Role.ROLE_USER);
            userRepository.save(user);
        }
    }
}
