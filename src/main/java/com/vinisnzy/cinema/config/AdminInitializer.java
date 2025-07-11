package com.vinisnzy.cinema.config;

import com.vinisnzy.cinema.enums.UserRole;
import com.vinisnzy.cinema.models.User;
import com.vinisnzy.cinema.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository repository) {
        return args -> {
            String username = "admin";
            String password = "admin";

            if (repository.findByUsername(username).isEmpty()) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(password);
                User admin = new User(null, username, encryptedPassword, UserRole.ADMIN);
                repository.save(admin);
            }
        };
    }
}
