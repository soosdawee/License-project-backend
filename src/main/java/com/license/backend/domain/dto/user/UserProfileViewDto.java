package com.license.backend.domain.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileViewDto {

    private Integer userId;

    private String firstname;

    private String lastname;

    private String description;

    private List<Integer> sharedVisualizationIds;

    private List<Integer> likedVisualizationIds;

    private byte[] profilePicture;

}
