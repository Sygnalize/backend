package com.echo.crypto.repository;

import com.echo.crypto.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUsername(String username);
    Optional<Profile> findByEmail(String email);

    boolean existsByUsername(String username);

}
