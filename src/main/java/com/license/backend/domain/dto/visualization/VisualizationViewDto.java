package com.license.backend.domain.dto.visualization;

import com.license.backend.domain.dto.visualization_model.VisualizationModelReducedViewDto;
import com.license.backend.domain.dto.visualization_model.VisualizationModelViewDto;
import com.license.backend.domain.model.VisualizationModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class VisualizationViewDto {

    private Integer visualizationId;

    private String title;

    private String article;

    private String backgroundColor;

    private String xAxisLabel;

    private String yAxisLabel;

    private Boolean showPercentages;

    private VisualizationModelReducedViewDto visualizationModelReducedViewDto;

}