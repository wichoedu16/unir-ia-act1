package com.romero.romero_act1.config;

import com.romero.romero_act1.security.AppUser;
import com.romero.romero_act1.security.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner ensureAdminUser(AppUserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (!repo.existsByUsername("admin")) {
                AppUser admin = new AppUser(
                        "admin",
                        encoder.encode("admin123"),
                        "ADMIN"
                );
                repo.save(admin);
            }
        };
    }
}
