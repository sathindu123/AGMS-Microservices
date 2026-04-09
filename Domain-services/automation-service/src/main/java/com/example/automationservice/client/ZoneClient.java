package com.example.automationservice.client;

import com.example.automationservice.dto.ApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zone-service" , url = "http://localhost:8081")
public interface ZoneClient {
    @GetMapping("/api/zones/{id}")
    ApiResponseDto getZoneById(@PathVariable("id") String id);
}
