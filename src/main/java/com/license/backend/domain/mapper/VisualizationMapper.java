package com.license.backend.domain.mapper;

import com.license.backend.domain.dto.visualization.*;
import com.license.backend.domain.model.*;
import lombok.Data;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.generated", unmappedTargetPolicy = ReportingPolicy.IGNORE, subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface VisualizationMapper {

    @SubclassMappings({
            @SubclassMapping(source = BarchartCreateDto.class, target = BarchartVisualization.class),
            @SubclassMapping(source = PiechartCreateDto.class, target = PiechartVisualization.class),
            @SubclassMapping(source = LinechartCreateDto.class, target = LinechartVisualization.class),
            @SubclassMapping(source = ScatterPlotCreateDto.class, target = ScatterPlotVisualization.class),
            @SubclassMapping(source = AreaChartCreateDto.class, target = AreaChartVisualization.class),
            @SubclassMapping(source = RaceChartCreateDto.class, target = RaceChartVisualization.class),
            @SubclassMapping(source = ElectionResultCreateDto.class, target = ElectionResultVisualization.class),
            @SubclassMapping(source = ElectionDonutCreateDto.class, target = ElectionDonutVisualization.class),
            @SubclassMapping(source = EuropeFilterCreateDto.class, target = EuropeFilterVisualization.class),
            @SubclassMapping(source = AfricaFilterCreateDto.class, target = AfricaFilterVisualization.class),
            @SubclassMapping(source = AsiaFilterCreateDto.class, target = AsiaFilterVisualization.class),
            @SubclassMapping(source = NorthAmericaFilterCreateDto.class, target = NorthAmericaFilterVisualization.class),
            @SubclassMapping(source = SouthAmericaFilterCreateDto.class, target = SouthAmericaFilterVisualization.class),
            @SubclassMapping(source = EuropeBubbleCreateDto.class, target = EuropeBubbleVisualization.class),
            @SubclassMapping(source = AsiaBubbleCreateDto.class, target = AsiaBubbleVisualization.class),
            @SubclassMapping(source = AfricaBubbleCreateDto.class, target = AfricaBubbleVisualization.class),
            @SubclassMapping(source = NorthAmericaBubbleCreateDto.class, target = NorthAmericaBubbleVisualization.class),
            @SubclassMapping(source = SouthAmericaBubbleCreateDto.class, target = SouthAmericaBubbleVisualization.class)
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
            @SubclassMapping(source = AreaChartVisualization.class, target = AreaChartViewDto.class),
            @SubclassMapping(source = RaceChartVisualization.class, target = RaceChartViewDto.class),
            @SubclassMapping(source = ElectionResultVisualization.class, target = ElectionResultViewDto.class),
            @SubclassMapping(source = ElectionDonutVisualization.class, target = ElectionDonutViewDto.class),
            @SubclassMapping(source = EuropeFilterVisualization.class, target = EuropeFilterViewDto.class),
            @SubclassMapping(source = AfricaFilterVisualization.class, target = AfricaFilterViewDto.class),
            @SubclassMapping(source = AsiaFilterVisualization.class, target = AsiaFilterViewDto.class),
            @SubclassMapping(source = NorthAmericaFilterVisualization.class, target = NorthAmericaFilterViewDto.class),
            @SubclassMapping(source = SouthAmericaFilterVisualization.class, target = SouthAmericaFilterViewDto.class),
            @SubclassMapping(source = EuropeBubbleVisualization.class, target = EuropeBubbleViewDto.class),
            @SubclassMapping(source = AsiaBubbleVisualization.class, target = AsiaBubbleViewDto.class),
            @SubclassMapping(source = AfricaBubbleVisualization.class, target = AfricaBubbleViewDto.class),
            @SubclassMapping(source = NorthAmericaBubbleVisualization.class, target = NorthAmericaBubbleViewDto.class),
            @SubclassMapping(source = SouthAmericaBubbleVisualization.class, target = SouthAmericaBubbleViewDto.class)
    })
    @Mapping(source = "visualizationModel", target = "visualizationModelReducedViewDto")
    VisualizationViewDto toViewDto(Visualization visualization);

    @Mapping(source = "visualizationModel", target = "visualizationModelReducedViewDto")
    Set<VisualizationReducedViewDto> toReducedViewDto(Set<Visualization> visualization);

}
