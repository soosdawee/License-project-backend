package com.license.backend.domain.dto.visualization;

import lombok.Data;

@Data
public class PiechartViewDto extends VisualizationViewDto {

    private Boolean showPercentages;

    private String colorPalette;

    private String customColors;

    private Boolean showLegend;

    private Integer transitionTime;

}
