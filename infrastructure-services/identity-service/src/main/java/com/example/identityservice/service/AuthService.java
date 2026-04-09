package com.example.identityservice.service;

import com.example.identityservice.dto.AuthRequestDto;
import com.example.identityservice.dto.UserRegisterDto;

public interface AuthService {
     String authenticate(AuthRequestDto authRequest);
     String register(UserRegisterDto user);
}
