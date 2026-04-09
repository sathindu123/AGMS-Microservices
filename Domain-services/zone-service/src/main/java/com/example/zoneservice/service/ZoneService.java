package com.example.zoneservice.service;

import com.example.zoneservice.dto.ZoneRequestDTO;
import com.example.zoneservice.dto.ZoneResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ZoneService {
    ZoneResponseDTO createZone(ZoneRequestDTO request);
    ZoneResponseDTO getZoneById(Long id);
    List<ZoneResponseDTO> getAllZones();
    ZoneResponseDTO updateZone(Long id, ZoneRequestDTO request);
    String deleteZone(Long id);
}
