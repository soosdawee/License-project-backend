package com.license.backend.controller;

import com.license.backend.domain.dto.comment.CommentCreateDto;
import com.license.backend.domain.dto.comment.CommentViewDto;
import com.license.backend.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public void createComment(@RequestBody CommentCreateDto createDto) {
        service.save(createDto);
    }

    @GetMapping("{visualizationId}")
    @SecurityRequirement(name = "bearerAuth")
    public List<CommentViewDto> getComments(@PathVariable Integer visualizationId) {
        return service.getAllByVisualizationId(visualizationId);
    }
}
