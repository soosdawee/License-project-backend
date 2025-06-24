package com.license.backend.domain.dto.comment;

import com.license.backend.domain.model.Comment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class CommentViewDto {

    private Integer commentId;

    private String content;

    private String firstname;

    private String lastname;

    private Integer parentId;

    private LocalDateTime createdAt;

}
