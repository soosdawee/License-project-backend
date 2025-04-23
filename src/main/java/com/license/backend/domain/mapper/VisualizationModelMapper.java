package com.license.backend.domain.mapper;

import com.license.backend.domain.dto.visualization_model.VisualizationModelCreateDto;
import com.license.backend.domain.dto.visualization_model.VisualizationModelViewDto;
import com.license.backend.domain.model.VisualizationModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassExhaustiveStrategy;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.generated", unmappedTargetPolicy = ReportingPolicy.IGNORE, subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface VisualizationModelMapper {

    VisualizationModel toEntity(VisualizationModelCreateDto visualizationModelCreateDto);

    VisualizationModelViewDto toViewDto(VisualizationModel visualizationModel);

}
