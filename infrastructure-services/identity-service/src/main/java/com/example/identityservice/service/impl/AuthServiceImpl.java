package com.example.identityservice.service.impl;

import com.example.identityservice.dto.AuthRequestDto;
import com.example.identityservice.dto.UserRegisterDto;
import com.example.identityservice.entity.UserCredentials;
import com.example.identityservice.repositroy.UserCredentialsRepository;
import com.example.identityservice.service.AuthService;
import com.example.identityservice.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserCredentialsRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String authenticate(AuthRequestDto authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("Invalid Access!");
        }
    }

    @Override
    public String register(UserRegisterDto user) {
        UserCredentials entity = UserCredentials.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        repository.save(entity);
        return "User registered successfully";
    }
}
