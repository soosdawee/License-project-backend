package com.license.backend.domain.dto.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NorthAmericaFilterCreateDto extends VisualizationCreateDto {

    private Boolean showLegend;

    private String colorPalette;

    private String customColors;

}
