package com.license.backend.domain.dto;

import com.license.backend.domain.model.Visualization;
import lombok.Data;

import java.util.List;

@Data
public class TableDataCreateDto {

    private List<List<String>> data;

    private Visualization visualization;

}
