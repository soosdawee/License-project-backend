package com.license.backend.domain.dto.visualization;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.license.backend.domain.constant.VisualizationTypes;
import com.license.backend.domain.dto.table_data.TableDataCreateDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "viz_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BarchartCreateDto.class, name = VisualizationTypes.BAR_CHART_NAME),
        @JsonSubTypes.Type(value = PiechartCreateDto.class, name = VisualizationTypes.PIE_CHART_NAME),
})
@Getter
public abstract class VisualizationCreateDto {

    private String title;

    private String article;

    private String backgroundColor;

    private List<TableDataCreateDto> tableDatas;

    private Integer visualizationModelId;

}
