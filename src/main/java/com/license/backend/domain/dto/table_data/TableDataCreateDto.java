package com.license.backend.domain.dto.table_data;

import com.license.backend.domain.model.Visualization;
import lombok.Data;

import java.util.List;

@Data
public class TableDataCreateDto {

    private List<List<String>> data;

    private Visualization visualization;

}
