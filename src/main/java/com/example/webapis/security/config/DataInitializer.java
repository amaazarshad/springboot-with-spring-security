package com.example.webapis.security.config;

import com.example.webapis.security.auth.AuthenticationService;
import com.example.webapis.security.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.webapis.security.user.Role.ADMIN;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@gmail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());
        };
    }
}
