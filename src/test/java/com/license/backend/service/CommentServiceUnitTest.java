package com.license.backend.service;

import com.license.backend.domain.dto.comment.CommentCreateDto;
import com.license.backend.domain.dto.comment.CommentResponseDto;
import com.license.backend.domain.dto.comment.CommentViewDto;
import com.license.backend.domain.mapper.CommentMapper;
import com.license.backend.domain.model.BarchartVisualization;
import com.license.backend.domain.model.Comment;
import com.license.backend.domain.model.User;
import com.license.backend.domain.model.Visualization;
import com.license.backend.repository.CommentRepository;
import com.license.backend.repository.UserRepository;
import com.license.backend.repository.VisualizationRepository;
import com.license.backend.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceUnitTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private VisualizationRepository visualizationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void whenCommentSaved_thenFlowAsExpected() {
        CommentCreateDto createDto = new CommentCreateDto();
        createDto.setContent("Great chart!");
        createDto.setVisualizationId(1);
        createDto.setUserId(2);

        User user = new User();
        user.setUserId(2);
        user.setFirstname("Johny");
        user.setLastname("Test");

        Visualization visualization = new BarchartVisualization();
        visualization.setVisualizationId(1);

        Comment commentEntity = new Comment();
        commentEntity.setContent("Great chart!");

        Comment savedComment = new Comment();
        savedComment.setCommentId(100);
        savedComment.setContent("Great chart!");
        savedComment.setAuthor(user);
        savedComment.setCreatedAt(LocalDateTime.now());

        when(commentMapper.toEntity(createDto)).thenReturn(commentEntity);
        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        when(visualizationRepository.findById(1)).thenReturn(Optional.of(visualization));
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        CommentResponseDto response = commentService.saveComment(createDto);

        assertEquals(100, response.getCommentId());
        assertEquals("Johny", response.getFirstname());
        assertEquals("Test", response.getLastname());
        assertEquals("Great chart!", response.getContent());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    void whenCommentsFetchedByVisualizatoin_thenFlowAsExpected() {
        Integer visualizationId = 42;
        User author = new User();
        author.setFirstname("Johny");
        author.setLastname("Test");

        Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setContent("Nice work!");
        comment.setAuthor(author);
        comment.setCreatedAt(LocalDateTime.now());

        when(commentRepository.findByVisualization_VisualizationId(visualizationId))
                .thenReturn(List.of(comment));

        List<CommentViewDto> result = commentService.getAllByVisualizationId(visualizationId);

        assertEquals(1, result.size());
        CommentViewDto dto = result.get(0);
        assertEquals(1, dto.getCommentId());
        assertEquals("Nice work!", dto.getContent());
        assertEquals("Johny", dto.getFirstname());
        assertEquals("Test", dto.getLastname());
        assertNotNull(dto.getCreatedAt());
    }

}
