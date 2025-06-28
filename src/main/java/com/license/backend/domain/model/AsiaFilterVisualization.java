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
@DiscriminatorValue("ASIA_FILTER")
public class AsiaFilterVisualization extends Visualization {

    @Column
    private Boolean showLegend;

    @Column
    private String colorPalette;

    @Column
    private String customColors;

}
