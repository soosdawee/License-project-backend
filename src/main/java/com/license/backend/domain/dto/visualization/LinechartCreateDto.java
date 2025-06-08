package com.license.backend.domain.dto.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinechartCreateDto extends VisualizationCreateDto{

    private String xAxisLabel;

    private String yAxisLabel;

    private Boolean areLabelsVisible;

    private Boolean showGrids;

    private String colorPalette;

    private String customColors;

    private Boolean showLegend;

    private Integer transitionTime;

}
