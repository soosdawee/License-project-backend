package com.license.backend.controller;

import com.license.backend.domain.dto.VisualizationCreateDto;
import com.license.backend.domain.dto.VisualizationViewAllDto;
import com.license.backend.domain.dto.VisualizationViewDto;
import com.license.backend.service.VisualizationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visualization")
@RequiredArgsConstructor
public class VisualizationController {

    private final VisualizationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public VisualizationViewDto createVisualization(@RequestBody VisualizationCreateDto visualizationCreateDto) {
        return service.create(visualizationCreateDto);
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "bearerAuth")
    public List<VisualizationViewAllDto> getAllVisualizations() {
        return service.getAll();
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public List<VisualizationViewDto> getVisualizations() {
        return service.get();
    }

}
