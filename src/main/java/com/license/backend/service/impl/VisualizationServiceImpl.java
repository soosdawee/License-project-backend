package com.license.backend.service.impl;

import com.license.backend.domain.dto.visualization.VisualizationCreateDto;
import com.license.backend.domain.dto.visualization.VisualizationViewDto;
import com.license.backend.domain.mapper.VisualizationMapper;
import com.license.backend.domain.model.Visualization;
import com.license.backend.repository.VisualizationModelRepository;
import com.license.backend.repository.VisualizationRepository;
import com.license.backend.service.VisualizationService;
import com.license.backend.util.ContextUtil;
import com.license.backend.util.ReflectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Service
public class VisualizationServiceImpl implements VisualizationService {

    private final VisualizationRepository repository;

    private final VisualizationModelRepository visualizationModelRepository;

    private final VisualizationMapper mapper;

    @Override
    @Transactional
    public VisualizationViewDto create(VisualizationCreateDto createDto) {
        Visualization visualization = mapper.toEntity(createDto);
        visualization.setUser(ContextUtil.getAuthenticatedUser());
        visualization.setVisualizationModel(
                visualizationModelRepository.findById(createDto.getVisualizationModelId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visualization model not found")));
        visualization.setTimestamp(LocalDateTime.now());
        visualization.setIsShared(false);
        visualization.setIsPublished(false);
        Visualization toReturn = repository.save(visualization);
        return mapper.toViewDto(toReturn);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisualizationViewDto> get() {
        return repository.findVisualizationsByUser(ContextUtil.getAuthenticatedUser()).stream()
                .map(mapper::toViewDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VisualizationViewDto get(Integer visualizationId) {
        return mapper.toViewDto(repository.findById(visualizationId).orElseThrow(() -> new RuntimeException("No visualization found!")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisualizationViewDto> getShared() {
        return repository.findSharedVisualizations().stream()
                .sorted(Comparator.comparing(Visualization::getTimestamp).reversed())
                .map(mapper::toViewDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(Integer visualizationId, VisualizationCreateDto visualizationCreateDto) {
        Visualization visualization = repository.findById(visualizationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visualization not found"));

        if (!visualization.getUser().getUserId().equals(ContextUtil.getAuthenticatedUser().getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this visualization");
        }

        ReflectionUtil.copyFields(visualizationCreateDto, visualization);

        visualization.setVisualizationModel(
                visualizationModelRepository.findById(visualizationCreateDto.getVisualizationModelId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visualization model not found"))
        );

        visualization.setTimestamp(LocalDateTime.now());

        repository.save(visualization);
    }

    @Override
    @Transactional
    public void updateIsPublished(Integer visualizationId) {
        Visualization visualization = repository.findById(visualizationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visualization not found"));

        if (!visualization.getUser().getUserId().equals(ContextUtil.getAuthenticatedUser().getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this visualization");
        }

        visualization.setIsPublished(!visualization.getIsPublished());

        repository.save(visualization);
    }

    @Override
    @Transactional
    public void updateIsShared(Integer visualizationId) {
        Visualization visualization = repository.findById(visualizationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visualization not found"));

        if (!visualization.getUser().getUserId().equals(ContextUtil.getAuthenticatedUser().getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this visualization");
        }

        visualization.setIsShared(!visualization.getIsShared());

        repository.save(visualization);
    }

    @Override
    public void report(Integer visualizationId) {
        Visualization visualization = repository.findById(visualizationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visualization not found"));

        visualization.setIsReported(true);

        repository.save(visualization);
    }

}
