package com.license.backend.service;

import com.license.backend.domain.dto.table_data.TableDataCreateDto;
import com.license.backend.domain.dto.table_data.TableDataViewDto;

import java.util.List;

public interface TableDataService {

    void create(TableDataCreateDto createDto);

    List<TableDataViewDto> getTableDatas();

    TableDataViewDto getTableData(Integer tableDataId);

}
