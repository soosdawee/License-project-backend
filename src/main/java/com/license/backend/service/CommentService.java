package com.license.backend.service;

import com.license.backend.domain.dto.comment.CommentCreateDto;
import com.license.backend.domain.dto.comment.CommentResponseDto;
import com.license.backend.domain.dto.comment.CommentViewDto;
import com.license.backend.domain.model.Comment;

import java.util.List;

public interface CommentService {

    void save(CommentCreateDto createDto);

    CommentResponseDto saveComment(CommentCreateDto createDto);

    List<CommentViewDto> getAll();

    List<CommentViewDto> getAllByVisualizationId(Integer visualizationId);

}
