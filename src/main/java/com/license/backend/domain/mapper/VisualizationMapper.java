package com.license.backend.domain.mapper;

import com.license.backend.domain.dto.visualization.*;
import com.license.backend.domain.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.generated", unmappedTargetPolicy = ReportingPolicy.IGNORE, subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface VisualizationMapper {

    @SubclassMappings({
            @SubclassMapping(source = BarchartCreateDto.class, target = BarchartVisualization.class),
            @SubclassMapping(source = PiechartCreateDto.class, target = PiechartVisualization.class),
            @SubclassMapping(source = LinechartCreateDto.class, target = LinechartVisualization.class),
            @SubclassMapping(source = ScatterPlotCreateDto.class, target = ScatterPlotVisualization.class),
            @SubclassMapping(source = AreaChartCreateDto.class, target = AreaChartVisualization.class)
    })
    @Mapping(target = "isShared", constant = "false")
    @Mapping(target = "isReported", constant = "false")
    @Mapping(target = "wasReviewedNegatively", constant = "false")
    Visualization toEntity(VisualizationCreateDto visualizationCreateDto);

    @SubclassMappings({
            @SubclassMapping(source = BarchartVisualization.class, target = BarchartViewDto.class),
            @SubclassMapping(source = PiechartVisualization.class, target = PiechartViewDto.class),
            @SubclassMapping(source = LinechartVisualization.class, target = LinechartViewDto.class),
            @SubclassMapping(source = ScatterPlotVisualization.class, target = ScatterPlotViewDto.class),
            @SubclassMapping(source = AreaChartVisualization.class, target = AreaChartViewDto.class)
    })
    @Mapping(source = "visualizationModel", target = "visualizationModelReducedViewDto")
    VisualizationViewDto toViewDto(Visualization visualization);

}
