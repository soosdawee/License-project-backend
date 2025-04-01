package com.license.backend.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class TableDataViewDto {

    private Integer tableDataId;

    private List<List<String>> data;

}
