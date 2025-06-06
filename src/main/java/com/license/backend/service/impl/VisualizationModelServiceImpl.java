package com.license.backend.service.impl;

import com.license.backend.domain.dto.visualization_model.VisualizationModelCreateDto;
import com.license.backend.domain.dto.visualization_model.VisualizationModelViewDto;
import com.license.backend.domain.mapper.VisualizationModelMapper;
import com.license.backend.domain.model.VisualizationModel;
import com.license.backend.repository.VisualizationModelRepository;
import com.license.backend.service.VisualizationModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Service
public class VisualizationModelServiceImpl implements VisualizationModelService {

    private final VisualizationModelRepository repository;

    private final VisualizationModelMapper mapper;

    @Override
    @Transactional
    public VisualizationModelViewDto create(VisualizationModelCreateDto createDto, MultipartFile file) throws IOException {
        VisualizationModel visualizationModel = mapper.toEntity(createDto);
        try {
            visualizationModel.setCardPhoto(file.getBytes());
        } catch (IOException e) {
            System.out.println("Card photo upload problem placeholder!");
        }
        return mapper.toViewDto(repository.save(visualizationModel));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisualizationModelViewDto> getAll() {
        return repository.findAll().stream().map(mapper::toViewDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VisualizationModelViewDto get(Integer visualizationModelId) {
        return repository.findById(visualizationModelId)
                .map(mapper::toViewDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visualization model not found"));
    }

    @Override
    @Transactional
    public VisualizationModelViewDto update(Integer visualizationModelId, Map<String, Object> fields) {
        Optional<VisualizationModel> visualizationModel = repository.findById(visualizationModelId);

        visualizationModel.ifPresent(model -> fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(VisualizationModel.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, model, value);
        }));

        return mapper.toViewDto(repository.save(visualizationModel.get()));
    }

}
