package com.license.backend.controller;

import com.license.backend.domain.dto.visualization.VisualizationCreateDto;
import com.license.backend.domain.dto.visualization.VisualizationViewDto;
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

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public List<VisualizationViewDto> getVisualizations() {
        return service.get();
    }

    //sajat
    @GetMapping("{visualizationId}")
    @SecurityRequirement(name = "bearerAuth")
    public VisualizationViewDto getVisualization(@PathVariable Integer visualizationId) {
        return service.get(visualizationId);
    }

    @GetMapping("/published/{visualizationId}")
    public VisualizationViewDto getPublishedVisualization(@PathVariable Integer visualizationId) {
        return service.getPublished(visualizationId);
    }

    @GetMapping("/shared/{visualizationId}")
    @SecurityRequirement(name = "bearerAuth")
    public VisualizationViewDto getSharedVisualization(@PathVariable Integer visualizationId) {
        return service.getShared(visualizationId);
    }

    @GetMapping("/shared")
    @SecurityRequirement(name = "bearerAuth")
    public List<VisualizationViewDto> getSharedVisualizations() {
        return service.getSharedVisualizationsOfUser();
    }

    @GetMapping("{userId}/shared")
    @SecurityRequirement(name = "bearerAuth")
    public List<VisualizationViewDto> getSharedVisualizations(@PathVariable Integer userId) {
        return service.getSharedVisualizationsOfUser(userId);
    }

    @PutMapping("{visualizationId}")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVisualization(@PathVariable Integer visualizationId, @RequestBody VisualizationCreateDto visualizationCreateDto) {
        service.update(visualizationId, visualizationCreateDto);
    }

    @PutMapping("{visualizationId}/published")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateIsPublished(@PathVariable Integer visualizationId) {
        service.updateIsPublished(visualizationId);
    }

    @PutMapping("{visualizationId}/shared")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateIsShared(@PathVariable Integer visualizationId) {
        service.updateIsShared(visualizationId);
    }

    @PutMapping("{visualizationId}/report")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reportVisualization(@PathVariable Integer visualizationId) {
        service.report(visualizationId);
    }


}
