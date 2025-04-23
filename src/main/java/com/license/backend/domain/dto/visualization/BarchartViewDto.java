package com.license.backend.domain.dto.visualization;

import lombok.Data;

@Data
public class BarchartViewDto extends VisualizationViewDto {

    private String xAxisLabel;

    private String yAxisLabel;

}
