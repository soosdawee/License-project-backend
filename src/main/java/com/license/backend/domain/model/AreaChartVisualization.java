package com.license.backend.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("AREA_CHART")
public class AreaChartVisualization extends Visualization {

    @Column
    private String xAxisLabel;

    @Column
    private String yAxisLabel;

    @Column
    private Boolean areLabelsVisible;

    @Column
    private Boolean showGrids;

    @Column
    private String colorPalette;

    @Column
    private String customColors;

}
