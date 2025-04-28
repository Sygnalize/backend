package com.echo.crypto.dto;

import com.echo.crypto.entities.Role;

import java.util.List;

public record ProfileDto(String username, String email, String password, List<Role> roles) {}
