package com.example.sensorservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.sensorservice.client.AutomationClient;
import com.example.sensorservice.client.ZoneClient;
import com.example.sensorservice.dto.ApiResponseDto;
import com.example.sensorservice.dto.ExternalTelemetryResponseDTO;
import com.example.sensorservice.dto.TelemetryRequestDTO;
import com.example.sensorservice.dto.ZoneResponseDTO;
import com.example.sensorservice.service.AuthService;
import com.example.sensorservice.service.SensorFetcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SensorFetcherServiceImpl implements SensorFetcherService {

    private final AuthService authService;
    private final WebClient webClient;
    private final ZoneClient zoneClient;
    private final AutomationClient automationClient;

    @Value("${external.iot.endpoints.telemetry}")
    private String telemetryEndpoint;

    @Scheduled(fixedRate = 10000)
    public void fetchAndPushTelemetry() {
        log.info("Starting reactive telemetry fetch...");

        String token = authService.getAccessToken();
        if (token == null) return;

        ApiResponseDto zonesResponse = zoneClient.getAllZones();
        ObjectMapper mapper = new ObjectMapper();
        List<ZoneResponseDTO> zones = mapper.convertValue(
                zonesResponse.getData(),
                new TypeReference<List<ZoneResponseDTO>>() {});

        Flux.fromIterable(zones)
                .flatMap(zone ->
                        webClient.get()
                                .uri(telemetryEndpoint, zone.getDeviceId())
                                .header("Authorization", "Bearer " + token)
                                .retrieve()
                                .bodyToMono(ExternalTelemetryResponseDTO.class)
                                .onErrorResume(e -> Mono.empty())

                                .publishOn(Schedulers.boundedElastic())

                                .doOnNext(externalData -> {
                                    TelemetryRequestDTO internalRequest = TelemetryRequestDTO.builder()
                                            .zoneId(zone.getId().toString())
                                            .currentTemp(externalData.getValue().getTemperature())
                                            .currentHumidity(externalData.getValue().getHumidity())
                                            .build();

                                    automationClient.sendTelemetry(internalRequest);
                                    log.info("Telemetry pushed for: {}", zone.getName());
                                })
                )
                .subscribe();
    }
}
