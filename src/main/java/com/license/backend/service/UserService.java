package com.license.backend.service;

import com.license.backend.domain.dto.user.*;

import java.util.List;

public interface UserService {

    void create(UserCreateDto createDto);

    List<UserViewDto> getUsers();

    UserLoginViewDto login(UserLoginDto loginDto);

    void likeVisualization(Integer visualizationId);

    UserProfileViewDto getUser(Integer userId);

}
