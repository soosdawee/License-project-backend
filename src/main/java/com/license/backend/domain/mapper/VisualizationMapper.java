package com.license.backend.domain.mapper;

import com.license.backend.domain.dto.*;
import com.license.backend.domain.model.BarchartVisualization;
import com.license.backend.domain.model.PiechartVisualization;
import com.license.backend.domain.model.Visualization;
import org.mapstruct.*;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.generated", unmappedTargetPolicy = ReportingPolicy.IGNORE, subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface VisualizationMapper {

    @SubclassMappings({
            @SubclassMapping(source = BarchartCreateDto.class, target = BarchartVisualization.class),
            @SubclassMapping(source = PiechartCreateDto.class, target = PiechartVisualization.class)
    })
    Visualization toEntity(VisualizationCreateDto visualizationCreateDto);

    VisualizationViewAllDto toViewAllDto(Visualization visualization);

    VisualizationViewDto toViewDto(Visualization visualization);

}
