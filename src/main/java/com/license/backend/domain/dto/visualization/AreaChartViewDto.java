package com.license.backend.domain.dto.visualization;

import lombok.Data;

@Data
public class AreaChartViewDto extends VisualizationViewDto {

    private String xAxisLabel;

    private String yAxisLabel;

    private Boolean areLabelsVisible;

    private Boolean showGrids;

    private String colorPalette;

    private String customColors;

}
