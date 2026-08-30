package com.example.bookingsystem.config;

import com.example.bookingsystem.model.Role;
import com.example.bookingsystem.model.User;
import com.example.bookingsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@lombok.extern.slf4j.Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    

    @Override
    public void run(String... args) throws Exception {
        String adminPass = System.getenv("SEED_ADMIN_PASSWORD");
        if (adminPass == null || adminPass.isEmpty()) {
            adminPass = java.util.UUID.randomUUID().toString();
            log.warn("SEED_ADMIN_PASSWORD not set. Using generated password: " + adminPass);
        }
        String userPass = System.getenv("SEED_USER_PASSWORD");
        if (userPass == null || userPass.isEmpty()) {
            userPass = java.util.UUID.randomUUID().toString();
            log.warn("SEED_USER_PASSWORD not set. Using generated password: " + userPass);
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
