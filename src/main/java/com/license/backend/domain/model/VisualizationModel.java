package com.license.backend.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "visualization_models")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisualizationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visualization_models_visualization_model_id_seq")
    @SequenceGenerator(name = "visualization_models_visualization_model_id_seq", allocationSize = 1)
    @Column
    private Integer visualizationModelId;

    @Lob
    private byte[] cardPhoto;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "visualization_model_columns",
            joinColumns = @JoinColumn(name = "visualization_model_id")
    )
    @Column(name = "column_name")
    private List<String> columnNames;


}
