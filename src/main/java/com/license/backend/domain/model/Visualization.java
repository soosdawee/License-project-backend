package com.license.backend.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "visualizations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "viz_type")
public abstract class Visualization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visualizations_visualization_id_seq")
    @SequenceGenerator(name = "visualizations_visualization_id_seq", allocationSize = 1)
    @Column
    private Integer visualizationId;

    @Column
    private String title;

    @Lob
    @Column
    private String article;

    @Column
    private String backgroundColor;

}
