package com.license.backend.domain.dto.comment;

import lombok.Data;

@Data
public class CommentCreateDto {

    private String content;

    private Integer visualizationId;

    private Integer parentId;

    private Integer userId;

}