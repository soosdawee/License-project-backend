package com.license.backend.service;

import com.license.backend.domain.dto.visualization_model.VisualizationModelCreateDto;
import com.license.backend.domain.dto.visualization_model.VisualizationModelViewDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface VisualizationModelService {

    VisualizationModelViewDto create(VisualizationModelCreateDto createDto, MultipartFile file) throws IOException;

    List<VisualizationModelViewDto> getAll();

    VisualizationModelViewDto get(Integer visualizationModelId);

    VisualizationModelViewDto update(Integer visualizationModelId, Map<String, Object> fields);

}
