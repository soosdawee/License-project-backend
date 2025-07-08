package com.license.backend.domain.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class UserProfileViewDto {

    private Integer userId;

    private String firstname;

    private String lastname;

    private String description;

    private byte[] profilePicture;

}
