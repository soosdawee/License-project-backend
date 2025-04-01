package com.license.backend.controller;

import com.license.backend.domain.dto.VisualizationCreateDto;
import com.license.backend.service.VisualizationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visualization")
@RequiredArgsConstructor
public class VisualizationController {

    private final VisualizationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public void createVisualization(@RequestBody VisualizationCreateDto visualizationCreateDto) {
        service.create(visualizationCreateDto);
    }

}
