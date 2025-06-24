package com.license.backend.service.impl;

import com.license.backend.domain.dto.comment.CommentCreateDto;
import com.license.backend.domain.dto.comment.CommentResponseDto;
import com.license.backend.domain.dto.comment.CommentViewDto;
import com.license.backend.domain.mapper.CommentMapper;
import com.license.backend.domain.model.Comment;
import com.license.backend.repository.CommentRepository;
import com.license.backend.repository.UserRepository;
import com.license.backend.repository.VisualizationRepository;
import com.license.backend.service.CommentService;
import com.license.backend.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final VisualizationRepository visualizationRepository;

    private final UserRepository userRepository;

    private final CommentMapper mapper;

    @Override
    public void save(CommentCreateDto createDto) {
        Comment comment = mapper.toEntity(createDto);
        comment.setAuthor(ContextUtil.getAuthenticatedUser());
        comment.setVisualization(visualizationRepository.findById(createDto.getVisualizationId())
                .orElseThrow(() -> new RuntimeException("Visualization not found")));
        if (createDto.getParentId() != null) {
            comment.setParent(repository.findById(createDto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found")));
        }
        comment.setCreatedAt(LocalDateTime.now());
        repository.save(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto saveComment(CommentCreateDto createDto) {
        Comment comment = mapper.toEntity(createDto);
        comment.setAuthor(userRepository.findById(createDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        comment.setVisualization(visualizationRepository.findById(createDto.getVisualizationId())
                .orElseThrow(() -> new RuntimeException("Visualization not found")));
        if (createDto.getParentId() != null) {
            comment.setParent(repository.findById(createDto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found")));
        }
        comment.setCreatedAt(LocalDateTime.now());
        Comment saved = repository.save(comment);
        CommentResponseDto responseDto = new CommentResponseDto();
        responseDto.setCommentId(saved.getCommentId());
        responseDto.setFirstname(saved.getAuthor().getFirstname());
        responseDto.setLastname(saved.getAuthor().getLastname());
        responseDto.setContent(saved.getContent());
        if (saved.getParent() != null) {
            responseDto.setParentId(saved.getParent().getCommentId());
        }
        responseDto.setCreatedAt(saved.getCreatedAt());
        return responseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentViewDto> getAll() {
        List<Comment> comments = repository.findAllWithRepliesAndAuthor();
        List<CommentViewDto> viewDtos = new ArrayList<>();

        for (Comment c : comments) {
            CommentViewDto viewDto = new CommentViewDto();
            viewDto.setCommentId(c.getCommentId());
            viewDto.setContent(c.getContent());
            viewDto.setFirstname(c.getAuthor().getFirstname());
            viewDto.setLastname(c.getAuthor().getLastname());
            viewDto.setParentId(
                    Optional.ofNullable(c.getParent())
                            .map(Comment::getCommentId)
                            .orElse(null)
            );
            viewDto.setCreatedAt(c.getCreatedAt());
            viewDtos.add(viewDto);
        }

        return viewDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentViewDto> getAllByVisualizationId(Integer visualizationId) {
        List<Comment> comments = repository.findByVisualization_VisualizationId(visualizationId);
        List<CommentViewDto> viewDtos = new ArrayList<>();

        for (Comment c : comments) {
            CommentViewDto viewDto = new CommentViewDto();
            viewDto.setCommentId(c.getCommentId());
            viewDto.setContent(c.getContent());
            viewDto.setFirstname(c.getAuthor().getFirstname());
            viewDto.setLastname(c.getAuthor().getLastname());
            viewDto.setParentId(
                    Optional.ofNullable(c.getParent())
                            .map(Comment::getCommentId)
                            .orElse(null)
            );
            viewDto.setCreatedAt(c.getCreatedAt());
            viewDtos.add(viewDto);
        }

        return viewDtos;
    }
}
