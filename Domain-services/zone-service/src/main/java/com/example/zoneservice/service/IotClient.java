package com.example.zoneservice.service;

import com.example.zoneservice.config.FeignClientConfig;
import com.example.zoneservice.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "external-iot-service",
        url = "http://104.211.95.241:8080/api",
        configuration = FeignClientConfig.class
)public interface IotClient {

    @PostMapping("/auth/login")
    AuthResponse login(@RequestBody AuthRequest request);

    @PostMapping("/devices")
    DeviceResponseDTO addDevice(
            @RequestHeader("Authorization") String token,
            @RequestBody DeviceRequestDTO request
    );

    @PostMapping("/auth/refresh")
    AuthResponse refreshToken(@RequestBody RefreshRequestDTO request);
}
