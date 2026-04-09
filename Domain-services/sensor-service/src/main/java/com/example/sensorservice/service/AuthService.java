package com.example.sensorservice.service;

public interface AuthService {
    String getAccessToken();
    String login();
    void clearToken();
}
