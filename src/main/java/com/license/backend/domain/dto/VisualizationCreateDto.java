package com.license.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "viz_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BarchartCreateDto.class, name = "BAR_CHART"),
        @JsonSubTypes.Type(value = PiechartCreateDto.class, name = "PIE_CHART"),
})
public abstract class VisualizationCreateDto {

    private String title;

    private String article;

    private String backgroundColor;

}
