package com.license.backend.domain.dto.visualization_model;

import lombok.Data;

import java.util.List;

@Data
public class VisualizationModelCreateDto {

    private List<String> columnNames;

}
