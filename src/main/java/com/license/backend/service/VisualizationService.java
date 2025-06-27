package com.license.backend.service;

import com.license.backend.domain.dto.visualization.VisualizationCreateDto;
import com.license.backend.domain.dto.visualization.VisualizationViewDto;

import java.util.List;

public interface VisualizationService {

    VisualizationViewDto create(VisualizationCreateDto createDto);

    List<VisualizationViewDto> get();

    VisualizationViewDto get(Integer visualizationId);

    VisualizationViewDto getPublished(Integer visualizationId);

    VisualizationViewDto getShared(Integer visualizationId);

    List<VisualizationViewDto> getSharedVisualizationsOfUser();

    List<VisualizationViewDto> getSharedVisualizationsOfUser(Integer userId);

    List<VisualizationViewDto> getReportedVisualizations();

    List<VisualizationViewDto> getNegativelyReviewed();

    void update(Integer visualizationId, VisualizationCreateDto visualizationCreateDto);

    void updateIsPublished(Integer visualizationId);

    void updateIsShared(Integer visualizationId);

    void report(Integer visualizationId);

    void unreport(Integer visualizationId);

    void reviewNegatively(Integer visualizationId);

    void delete(Integer visualizationId);

    void adminDelete(Integer visualizationId);

}
