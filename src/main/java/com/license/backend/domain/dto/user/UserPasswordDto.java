package com.license.backend.domain.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPasswordDto {

    private String oldPassword;

    private String newPassword;

    private String newPasswordRepeated;

}
