package com.echo.crypto.service;

import com.echo.crypto.dto.ProfileDto;
import com.echo.crypto.entities.Profile;
import com.echo.crypto.mapper.ProfileMapper;
import com.echo.crypto.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public ProfileDto findByUsername(String username) {
        Profile profile = profileRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found: " + username));
        return profileMapper.toDTo(profile);
    }

    public boolean existsByUsername(String username) {
        return profileRepository.existsByUsername(username);
    }

    public Profile findByEmail(String email) {
        return profileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(("Profile not found " + email)));
    }

    public Profile registerProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
