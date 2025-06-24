package com.license.backend.domain.dto.table_data;

import lombok.Data;

import java.util.List;

@Data
public class TableDataCreateDto {

    private List<List<String>> data;

    private String sheetsLink;

}
