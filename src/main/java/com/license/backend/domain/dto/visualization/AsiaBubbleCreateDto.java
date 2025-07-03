package com.license.backend.domain.dto.visualization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AsiaBubbleCreateDto extends VisualizationCreateDto {

    private String barColor;

}
