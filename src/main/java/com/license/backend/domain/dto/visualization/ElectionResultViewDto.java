package com.license.backend.domain.dto.visualization;

import lombok.Data;

@Data
public class ElectionResultViewDto extends VisualizationViewDto{

    private String colorPalette;

    private String customColors;

    private Boolean showLegend;

    private Integer transitionTime;

}
