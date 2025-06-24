package com.license.backend.controller;

import com.license.backend.domain.dto.comment.CommentCreateDto;
import com.license.backend.domain.dto.comment.CommentResponseDto;
import com.license.backend.domain.dto.comment.CommentViewDto;
import com.license.backend.domain.model.Comment;
import com.license.backend.service.CommentService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CommentWebSocketController {

    private final CommentService service;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/comments/{visualizationId}/create")
    public void createComment(
            @DestinationVariable Integer visualizationId,
            CommentCreateDto createDto) {
        CommentResponseDto saved = service.saveComment(createDto);

        messagingTemplate.convertAndSend(
                "/topic/comments/" + visualizationId,
                saved
        );
    }

    @MessageMapping("/comments/{visualizationId}/typing")
    public void userTyping(@DestinationVariable Integer visualizationId, Principal principal) {
        if (principal == null) return;
        String username = principal.getName();
        messagingTemplate.convertAndSend(
                "/topic/comments/" + visualizationId + "/typing",
                username
        );
    }

}

