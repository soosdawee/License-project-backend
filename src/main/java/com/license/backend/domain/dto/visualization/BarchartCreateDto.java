package com.license.backend.domain.dto.visualization;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarchartCreateDto extends VisualizationCreateDto {

    private String xAxisLabel;

    private String yAxisLabel;

    private Boolean areLabelsVisible;

    private Boolean showGrids;

    private String barColor;

    private String customBarColors;

    private Integer spacing;

}
