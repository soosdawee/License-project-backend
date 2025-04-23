package com.license.backend.controller;

import com.license.backend.domain.dto.table_data.TableDataCreateDto;
import com.license.backend.domain.dto.table_data.TableDataViewDto;
import com.license.backend.service.TableDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/table_data")
@RequiredArgsConstructor
public class TableDataController {

    private final TableDataService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTableData(@RequestBody TableDataCreateDto tableDataCreateDto) {
        service.create(tableDataCreateDto);
    }

    @GetMapping
    public List<TableDataViewDto> getTableDatas() {
        return service.getTableDatas();
    }

    @GetMapping("{id}")
    public TableDataViewDto getTableDatas(@PathVariable("id") Integer id) {
        return service.getTableData(id);
    }

}
