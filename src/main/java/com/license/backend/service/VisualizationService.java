package com.license.backend.service;

import com.license.backend.domain.dto.VisualizationCreateDto;
import com.license.backend.domain.dto.VisualizationViewAllDto;
import com.license.backend.domain.dto.VisualizationViewDto;

import java.util.List;

public interface VisualizationService {

    VisualizationViewDto create(VisualizationCreateDto createDto);

    List<VisualizationViewAllDto> getAll();

    List<VisualizationViewDto> get();

}
