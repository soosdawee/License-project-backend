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
@DiscriminatorValue("PIE_CHART")
public class PiechartVisualization extends Visualization {

    @Column
    private Boolean showPercentages;

    @Column
    private String colorPalette;

    @Column
    private String customColors;

    @Column
    private Boolean showLegend;

    @Column
    private Integer transitionTime;

}
