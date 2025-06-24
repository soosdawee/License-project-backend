package com.license.backend.domain.dto.comment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class CommentResponseDto {

    Integer commentId;

    String firstname;

    String lastname;

    String content;

    Integer parentId;

    LocalDateTime createdAt;

}
