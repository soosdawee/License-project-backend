package com.license.backend.domain.mapper;

import com.license.backend.domain.dto.TableDataCreateDto;
import com.license.backend.domain.dto.TableDataViewDto;
import com.license.backend.domain.model.TableData;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.generated", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TableDataMapper {

    TableData toEntity(TableDataCreateDto createDto);

    TableDataViewDto toViewDto(TableData tableData);

}
