package com.license.backend.domain.dto.visualization;

import lombok.Data;

@Data
public class ElectionDonutViewDto extends VisualizationViewDto{

    private String colorPalette;

    private String customColors;

    private Boolean showLegend;

}
