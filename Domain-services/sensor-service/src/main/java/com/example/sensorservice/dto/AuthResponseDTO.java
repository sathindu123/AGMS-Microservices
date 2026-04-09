package com.example.sensorservice.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String name;
    private String accessToken;
    private String refreshToken;
}
