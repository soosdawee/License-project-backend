package com.license.backend.domain.dto.visualization;

import lombok.Data;

@Data
public class PiechartCreateDto extends VisualizationCreateDto {

    private Boolean showPercentages;

}
