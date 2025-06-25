package com.license.backend.service;

import com.license.backend.domain.dto.user.*;
import com.license.backend.domain.dto.visualization.VisualizationViewDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    void create(UserCreateDto createDto);

    List<UserViewDto> getUsers();

    UserLoginViewDto login(UserLoginDto loginDto);

    void likeVisualization(Integer visualizationId);

    UserProfileViewDto getUser(Integer userId);

    List<VisualizationViewDto> getLiked(Integer userId);

    void updateProfilePicture(MultipartFile file) throws IOException;

    void update(UserUpdateDto updateDto);

    void updatePassword(UserPasswordDto updateDto);

}
