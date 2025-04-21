package com.license.backend.service.impl;

import com.license.backend.domain.dto.TableDataCreateDto;
import com.license.backend.domain.dto.TableDataViewDto;
import com.license.backend.domain.mapper.TableDataMapper;
import com.license.backend.repository.TableDataRepository;
import com.license.backend.service.TableDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Service
public class TableDataServiceImpl implements TableDataService {

    private final TableDataRepository repository;

    private final TableDataMapper mapper;

    @Override
    @Transactional
    public void create(TableDataCreateDto createDto) {
        repository.save(mapper.toEntity(createDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableDataViewDto> getTableDatas() {
        return repository.findAll().stream().map(mapper::toViewDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TableDataViewDto getTableData(Integer tableDataId) {
        return repository.findById(tableDataId).map(mapper::toViewDto).orElse(null);
    }

}
