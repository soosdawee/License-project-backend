package com.license.backend.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column
    private Integer titleSize;

    @Column
    private String font;

    @Lob
    @Column
    private String article;

    @Column
    private Integer articleSize;

    @Column
    private String backgroundColor;

    @Column
    private Boolean isShared;

    @Column
    private Boolean showLegend;

    @Column
    private Boolean showAnnotations;

    @Column
    private Boolean isAnnotationCustom;

    @Column
    private String customAnnotation;

    @Column
    private Boolean isFooter;

    @Column
    private String footerText;

    @Column
    private LocalDateTime timestamp;

    @Column
    private Integer opacity;

    @Column
    private String textColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_visualization_id", referencedColumnName = "visualizationId")
    private List<TableData> tableDatas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visualization_model_id")
    private VisualizationModel visualizationModel;

}
