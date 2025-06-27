package com.license.backend.service.impl;

import com.license.backend.domain.dto.visualization.VisualizationCreateDto;
import com.license.backend.domain.dto.visualization.VisualizationViewDto;
import com.license.backend.domain.mapper.VisualizationMapper;
import com.license.backend.domain.model.Visualization;
import com.license.backend.exception.InaccessibleException;
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
        Visualization visualization = repository.findById(visualizationId).orElseThrow(() -> new RuntimeException("No visualization found!"));
        if (visualization.getUser().getUserId().equals(ContextUtil.getAuthenticatedUser().getUserId())) {
            return mapper.toViewDto(visualization);
        } else {
            throw new InaccessibleException("This visualization was not published or shared with you!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public VisualizationViewDto getPublished(Integer visualizationId) {
        Visualization visualization = repository.findById(visualizationId).orElseThrow(() -> new RuntimeException("No visualization found!"));
        if (visualization.getIsPublished()) {
            return mapper.toViewDto(visualization);
        }
        else {
            throw new InaccessibleException("This visualization was not published!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public VisualizationViewDto getShared(Integer visualizationId) {
        Visualization visualization = repository.findById(visualizationId).orElseThrow(() -> new RuntimeException("No visualization found!"));
        if (visualization.getIsShared()) {
            return mapper.toViewDto(visualization);
        }
        else {
            throw new InaccessibleException("This visualization was not shared with you!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisualizationViewDto> getSharedVisualizationsOfUser() {
        return repository.findSharedVisualizations().stream()
                .sorted(Comparator.comparing(Visualization::getTimestamp).reversed())
                .map(mapper::toViewDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisualizationViewDto> getSharedVisualizationsOfUser(Integer userId) {
        return repository.findSharedVisualizationsByUserId(userId).stream()
                .sorted(Comparator.comparing(Visualization::getTimestamp).reversed())
                .map(mapper::toViewDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisualizationViewDto> getReportedVisualizations() {
        return repository.findReportedVisualizations().stream()
                .map(mapper::toViewDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisualizationViewDto> getNegativelyReviewed() {
        return repository.findNegativelyReviewedVisualizations().stream()
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

    @Override
    @Transactional
    public void unreport(Integer visualizationId) {
        Visualization visualization = repository.findById(visualizationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visualization not found"));

        visualization.setIsReported(false);

        repository.save(visualization);
    }

    @Override
    @Transactional
    public void reviewNegatively(Integer visualizationId) {
        Visualization visualization = repository.findById(visualizationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visualization not found"));

        visualization.setWasReviewedNegatively(true);

        repository.save(visualization);
    }

    @Override
    @Transactional
    public void delete(Integer visualizationId) {
        Visualization visualization = repository.findById(visualizationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visualization not found"));
        if (visualization.getUser().getUserId().equals(ContextUtil.getAuthenticatedUser().getUserId())) {
            repository.delete(visualization);
        } else {
            throw new InaccessibleException("This visualization does not belong to you!");
        }
    }

    @Override
    @Transactional
    public void adminDelete(Integer visualizationId) {
        repository.deleteById(visualizationId);
    }

}
