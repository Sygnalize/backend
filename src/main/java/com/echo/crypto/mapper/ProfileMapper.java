package com.echo.crypto.mapper;

import com.echo.crypto.dto.ProfileDto;
import com.echo.crypto.entities.Profile;
import com.echo.crypto.entities.Role;
import com.echo.crypto.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    private final RoleRepository roleRepository;

    public Profile toEntity(ProfileDto dto) {
        Profile profile = new Profile();
        profile.setUsername(dto.username());
        profile.setEmail(dto.email());
        profile.setPassword(dto.password());

        List<Role> persistentRoles = dto.roles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role.getName())))
                .collect(Collectors.toList());

        profile.setRoles(persistentRoles);
        return profile;
    }

    public ProfileDto toDTo(Profile profile) {
        return new ProfileDto(
                profile.getUsername(),
                profile.getEmail(),
                profile.getPassword(),
                profile.getRoles()
        );
    }
}
