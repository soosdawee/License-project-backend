package com.license.backend.controller;

import com.license.backend.domain.dto.visualization_model.VisualizationModelCreateDto;
import com.license.backend.domain.dto.visualization_model.VisualizationModelViewDto;
import com.license.backend.service.VisualizationModelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/visualization_model")
@RequiredArgsConstructor
public class VisualizationModelController {

    private final VisualizationModelService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public VisualizationModelViewDto createVisualization(
            @RequestPart("visualizationModelCreateDto") VisualizationModelCreateDto visualizationModelCreateDto,
            @RequestPart("file") MultipartFile file) throws IOException {
        return service.create(visualizationModelCreateDto, file);
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public List<VisualizationModelViewDto> getVisualizationModels() {
        return service.getAll();
    }

    @GetMapping("{visualizationModelId}")
    @SecurityRequirement(name = "bearerAuth")
    public VisualizationModelViewDto getVisualizationModel(@PathVariable Integer visualizationModelId) {
        return service.get(visualizationModelId);
    }

    @PatchMapping("{visualizationModelId}")
    @SecurityRequirement(name = "bearerAuth")
    public VisualizationModelViewDto updateVisualizationModel(@PathVariable Integer visualizationModelId, @RequestBody Map<String, Object> fields) {
        return service.update(visualizationModelId, fields);
    }


}
