package com.example.cropservice.service;

import com.example.cropservice.dto.CropRequestDTO;
import com.example.cropservice.dto.CropResponseDTO;

import java.util.List;

public interface CropService {
    String createCrop(CropRequestDTO cropResponseDto);
    String updateCrop(String id);
    List<CropResponseDTO> getAllCrops();
}
