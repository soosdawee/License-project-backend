package com.license.backend.service;

import com.license.backend.domain.dto.user.*;
import com.license.backend.domain.dto.visualization.VisualizationReducedViewDto;
import com.license.backend.domain.dto.visualization.VisualizationViewDto;
import org.springframework.messaging.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

public interface UserService {

    void create(UserCreateDto createDto);

    List<UserViewDto> getUsers();

    UserLoginViewDto login(UserLoginDto loginDto);

    void likeVisualization(Integer visualizationId);

    UserProfileViewDto getUser(Integer userId);

    Set<VisualizationReducedViewDto> getLiked(Integer userId);

    void updateProfilePicture(MultipartFile file) throws IOException;

    void update(UserUpdateDto updateDto);

    void updatePassword(UserPasswordDto updateDto);

    void softDelete();

    void delete(Integer userId);

    void processForgotPassword(String email) throws MessagingException, UnsupportedEncodingException, jakarta.mail.MessagingException;

    void processResetPassword(String token, String password);

}