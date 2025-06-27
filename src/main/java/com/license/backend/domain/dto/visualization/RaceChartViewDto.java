package com.license.backend.domain.dto.visualization;

import lombok.Data;

@Data
public class RaceChartViewDto extends VisualizationViewDto {

    private String colorPalette;

    private Boolean showLegend;

}
