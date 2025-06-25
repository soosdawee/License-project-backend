package com.license.backend.domain.dto.user;

import lombok.Data;

@Data
public class UserPasswordDto {

    private String oldPassword;

    private String newPassword;

    private String newPasswordRepeated;

}
