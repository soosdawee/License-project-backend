package com.license.backend.domain.dto.visualization_model;

import lombok.Data;

import java.util.List;

@Data
public class VisualizationModelReducedViewDto {

    private Integer visualizationModelId;

    private List<String> columnNames;

}
