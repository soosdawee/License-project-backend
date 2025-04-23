package com.license.backend.domain.dto.table_data;

import lombok.Data;

import java.util.List;

@Data
public class TableDataViewDto {

    private Integer tableDataId;

    private List<List<String>> data;

}
