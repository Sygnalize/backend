package com.echo.crypto.auth;

import com.echo.crypto.dto.ProfileDto;
import com.echo.crypto.entities.Profile;
import com.echo.crypto.exception.InvalidPasswordException;
import com.echo.crypto.mapper.ProfileMapper;
import com.echo.crypto.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;
    private final ProfileMapper profileMapper;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ProfileDto request) {
        if (profileService.existsByUsername(request.username())) {
            return ResponseEntity.status(409).body(Map.of("error", "Username already taken"));
        }
        Profile profile = profileMapper.toEntity(request);
        profile.setPassword(passwordEncoder.encode(request.password()));
        profileService.registerProfile(profile); // в репозитории вызывается profileRepository.save(profile);
        return ResponseEntity.ok(Map.of("message", "User registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Profile profile = profileService.findByEmail(email);

        if (!passwordEncoder.matches(password, profile.getPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }

        UserDetails user = userDetailsService.loadUserByUsername(profile.getUsername());
        String accessToken = jwtService.generateToken(user.getUsername(), 15 * 60 * 1000);  // 15 мин
        String refreshToken = jwtService.generateToken(user.getUsername(), 7 * 24 * 60 * 60 * 1000); // 7 дней

        return ResponseEntity.ok(Map.of( // Засунуть в дто
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> payload) {
        String refreshToken = payload.get("refreshToken");
        if (!jwtService.isTokenValid(refreshToken)) return ResponseEntity.status(401).build();

        String username = jwtService.extractUsername(refreshToken);
        String newAccessToken = jwtService.generateToken(username, 15 * 60 * 1000);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}

