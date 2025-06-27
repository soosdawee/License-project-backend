package com.license.backend.domain.dto.user;

import com.license.backend.domain.constant.Roles;
import com.license.backend.domain.dto.visualization.VisualizationReducedViewDto;
import com.license.backend.domain.dto.visualization.VisualizationViewDto;
import com.license.backend.domain.model.Visualization;
import lombok.Data;

import java.util.Set;

@Data
public class UserViewDto {

    private Integer userId;

    private String firstname;

    private String lastname;

    private String email;

    private Roles userType;

    private Boolean isActive;

    private byte[] profilePicture;

    private Set<VisualizationReducedViewDto> likedVisualizations;

}
