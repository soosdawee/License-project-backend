package com.license.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarchartCreateDto extends VisualizationCreateDto {

    private String xAxisLabel;

    private String yAxisLabel;

}
