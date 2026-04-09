package com.example.zoneservice.service;

public interface ExternalAuthService {
     String getAccessToken();
     void refreshAccessToken();

}
