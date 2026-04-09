package com.example.identityservice.controller;



import com.example.identityservice.dto.ApiResponseDto;
import com.example.identityservice.dto.AuthRequestDto;
import com.example.identityservice.dto.UserRegisterDto;
import com.example.identityservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto> addNewUser(@RequestBody UserRegisterDto user) {
        return ResponseEntity.ok(
                new ApiResponseDto(
                        201,
                        "User registered successfully",
                        service.register(user)
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody AuthRequestDto authRequest) {
        return ResponseEntity.ok(
                new ApiResponseDto(
                        200,
                        "User login successfully",
                        service.authenticate(authRequest)
                )
        );
    }
}