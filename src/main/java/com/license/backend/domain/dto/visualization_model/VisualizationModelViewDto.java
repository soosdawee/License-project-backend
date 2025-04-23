package com.license.backend.domain.dto.visualization_model;

import lombok.Data;

import java.util.List;

@Data
public class VisualizationModelViewDto {

    private Integer visualizationModelId;

    private byte[] cardPhoto;

    private List<String> columnNames;

}
