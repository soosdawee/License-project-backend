package com.license.backend.domain.dto.user;

import com.license.backend.domain.constant.Roles;
import com.license.backend.domain.model.Visualization;
import lombok.Data;

import java.util.Set;

@Data
public class UserViewDto {

    private Integer userId;

    private String firstname;

    private String lastname;

    private String email;

    private String userPassword;

    private Roles userType;

    private Boolean isActive;

    private Set<Visualization> likedVisualizations;

}
