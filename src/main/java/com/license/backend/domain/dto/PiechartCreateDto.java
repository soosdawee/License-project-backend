package com.license.backend.domain.dto;

import lombok.Data;

@Data
public class PiechartCreateDto extends VisualizationCreateDto {

    private Boolean showPercentages;

}
