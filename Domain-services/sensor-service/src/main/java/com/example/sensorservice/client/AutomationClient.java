package com.example.sensorservice.client;

import com.example.sensorservice.dto.ApiResponseDto;
import com.example.sensorservice.dto.TelemetryRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "automation-service")
public interface AutomationClient {
    @PostMapping("/api/automation/process")
    ApiResponseDto sendTelemetry(@RequestBody TelemetryRequestDTO request);
}
