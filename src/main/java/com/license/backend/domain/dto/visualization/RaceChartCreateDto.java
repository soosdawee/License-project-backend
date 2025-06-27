package com.license.backend.domain.dto.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RaceChartCreateDto extends VisualizationCreateDto{

    private String colorPalette;

    private Boolean showLegend;

}
