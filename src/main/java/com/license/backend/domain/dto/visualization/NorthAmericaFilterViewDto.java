package com.license.backend.domain.dto.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NorthAmericaFilterViewDto extends VisualizationViewDto {

    private Boolean showLegend;

    private String colorPalette;

    private String customColors;

}
