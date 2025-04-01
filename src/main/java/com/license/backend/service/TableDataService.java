package com.license.backend.service;

import com.license.backend.domain.dto.TableDataCreateDto;
import com.license.backend.domain.dto.TableDataViewDto;

import java.util.List;

public interface TableDataService {

    void create(TableDataCreateDto createDto);

    List<TableDataViewDto> getTableDatas();

    TableDataViewDto getTableData(Integer tableDataId);

}
