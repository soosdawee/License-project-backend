package com.license.backend.service;

import com.license.backend.domain.dto.visualization.VisualizationCreateDto;
import com.license.backend.domain.dto.visualization.VisualizationViewDto;

import java.util.List;

public interface VisualizationService {

    VisualizationViewDto create(VisualizationCreateDto createDto);

    List<VisualizationViewDto> get();

    VisualizationViewDto get(Integer visualizationId);

}
