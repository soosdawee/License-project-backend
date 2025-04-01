package com.license.backend.domain.dto;

import com.license.backend.domain.constant.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginViewDto {

    Integer userId;

    String email;

    Roles userType;

    String jwt;

}
