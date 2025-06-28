package com.license.backend.domain.dto.visualization;

import lombok.Data;

@Data
public class AsiaFilterViewDto extends VisualizationViewDto {

    private Boolean showLegend;

    private String colorPalette;

    private String customColors;

}