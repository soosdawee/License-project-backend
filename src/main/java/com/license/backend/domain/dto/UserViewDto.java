package com.license.backend.domain.dto;

import com.license.backend.domain.constant.Roles;
import lombok.Data;

@Data
public class UserViewDto {

    private Integer userId;

    private String firstname;

    private String lastname;

    private String email;

    private String userPassword;

    private Roles userType;

    private Boolean isActive;

}
