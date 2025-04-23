package com.license.backend.service;

import com.license.backend.domain.dto.user.UserCreateDto;
import com.license.backend.domain.dto.user.UserLoginDto;
import com.license.backend.domain.dto.user.UserLoginViewDto;
import com.license.backend.domain.dto.user.UserViewDto;

import java.util.List;

public interface UserService {

    void create(UserCreateDto createDto);

    List<UserViewDto> getUsers();

    UserLoginViewDto login(UserLoginDto loginDto);

}
