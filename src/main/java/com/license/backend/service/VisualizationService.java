package com.license.backend.service;

import com.license.backend.domain.dto.VisualizationCreateDto;
import com.license.backend.domain.dto.VisualizationViewDto;

public interface VisualizationService {

    VisualizationViewDto create(VisualizationCreateDto createDto);

}
