package com.license.backend.domain.dto;

import com.license.backend.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisualizationViewAllDto {

    private Integer visualizationId;

    private String title;

    private String article;

    private String backgroundColor;

    private User user;

    private String xAxisLabel;

    private String yAxisLabel;

    private Boolean showPercentages;

}
