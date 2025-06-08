package com.license.backend.domain.dto.visualization;

import com.license.backend.domain.dto.visualization_model.VisualizationModelReducedViewDto;
import com.license.backend.domain.model.TableData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class VisualizationViewDto {

    private Integer visualizationId;

    private String title;

    private Integer titleSize;

    private String font;

    private String article;

    private Integer articleSize;

    private String backgroundColor;

    private Boolean showLegend;

    private Boolean showAnnotations;

    private Boolean isAnnotationCustom;

    private String customAnnotation;

    private Boolean isFooter;

    private String footerText;

    private String xAxisLabel;

    private String yAxisLabel;

    private Boolean showPercentages;

    private Boolean isShared;

    private LocalDateTime timestamp;

    private Integer opacity;

    private String textColor;

    private VisualizationModelReducedViewDto visualizationModelReducedViewDto;

    private List<TableData> tableDatas;

}