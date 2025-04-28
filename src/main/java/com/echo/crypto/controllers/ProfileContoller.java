package com.echo.crypto.controllers;

import com.echo.crypto.entities.Profile;
import com.echo.crypto.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/profiles")
public class ProfileContoller {

    private ProfileService profileService;

    public ProfileContoller(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileService.findAll();
        return ResponseEntity.ok(profiles);
    }
}
