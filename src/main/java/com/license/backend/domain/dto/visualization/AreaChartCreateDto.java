package com.license.backend.domain.dto.visualization;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AreaChartCreateDto extends VisualizationCreateDto {

    private String xAxisLabel;

    private String yAxisLabel;

    private Boolean areLabelsVisible;

    private Boolean showGrids;

    private String colorPalette;

    private String customColors;

}
