package com.license.backend.domain.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDto {

    private String firstname;

    private String lastname;

    private String description;

}
