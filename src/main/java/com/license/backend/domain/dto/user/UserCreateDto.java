package com.license.backend.domain.dto.user;

import lombok.Data;

@Data
public class UserCreateDto {

    private String firstname;

    private String lastname;

    private String email;

    private String userPassword;

}
