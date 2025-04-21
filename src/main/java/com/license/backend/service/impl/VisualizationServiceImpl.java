package com.license.backend.service.impl;

import com.license.backend.domain.dto.VisualizationCreateDto;
import com.license.backend.domain.dto.VisualizationViewAllDto;
import com.license.backend.domain.dto.VisualizationViewDto;
import com.license.backend.domain.mapper.VisualizationMapper;
import com.license.backend.domain.model.Visualization;
import com.license.backend.repository.VisualizationRepository;
import com.license.backend.service.VisualizationService;
import com.license.backend.util.ContextUtil;
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
public class VisualizationServiceImpl implements VisualizationService {

    private final VisualizationRepository repository;

    private final VisualizationMapper mapper;

    @Override
    @Transactional
    public VisualizationViewDto create(VisualizationCreateDto createDto) {
        Visualization visualization = mapper.toEntity(createDto);
        visualization.setUser(ContextUtil.getAuthenticatedUser());
        Visualization saved = repository.save(visualization);
        return mapper.toViewDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisualizationViewAllDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toViewAllDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisualizationViewDto> get() {
        return repository.findVisualizationsByUser(ContextUtil.getAuthenticatedUser()).stream()
                .map(mapper::toViewDto)
                .collect(Collectors.toList());
    }

}
