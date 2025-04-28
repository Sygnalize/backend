package com.echo.crypto.seeds;

import com.echo.crypto.entities.Role;
import com.echo.crypto.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RoleSeeder {

    @Bean
    public CommandLineRunner seedRoles(RoleRepository roleRepository) {
        return args -> {
            List<String> roles = List.of(
                    "ROLE_ADMIN",
                    "ROLE_MODERATOR",
                    "ROLE_USER",
                    "ROLE_GUEST"
            );

            for (String roleName : roles) {
                roleRepository.findByName(roleName)
                        .orElseGet(() -> roleRepository.save(new Role(null, roleName)));
            }
        };
    }
}
