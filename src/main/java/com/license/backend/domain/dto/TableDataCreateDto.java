package com.license.backend.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class TableDataCreateDto {

    private List<List<String>> data;

}
