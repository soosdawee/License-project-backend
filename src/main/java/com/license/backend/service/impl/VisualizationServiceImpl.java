package com.license.backend.service.impl;

import com.license.backend.domain.dto.VisualizationCreateDto;
import com.license.backend.domain.mapper.VisualizationMapper;
import com.license.backend.domain.model.Visualization;
import com.license.backend.repository.VisualizationRepository;
import com.license.backend.service.VisualizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
@Slf4j
@Service
public class VisualizationServiceImpl implements VisualizationService {

    private final VisualizationRepository repository;

    private final VisualizationMapper mapper;

    @Override
    public void create(VisualizationCreateDto createDto) {
        Visualization visualization = mapper.toEntity(createDto);
        repository.save(visualization);
    }

}
