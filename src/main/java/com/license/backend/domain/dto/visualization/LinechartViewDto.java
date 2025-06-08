package com.license.backend.domain.dto.visualization;

import lombok.Data;

@Data
public class LinechartViewDto extends VisualizationViewDto {

    private String xAxisLabel;

    private String yAxisLabel;

    private Boolean areLabelsVisible;

    private Boolean showGrids;

    private String colorPalette;

    private String customColors;

    private Boolean showLegend;

    private Integer transitionTime;

}
