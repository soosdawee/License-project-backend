package com.license.backend.service;

import com.license.backend.domain.dto.UserCreateDto;
import com.license.backend.domain.dto.UserLoginDto;
import com.license.backend.domain.dto.UserLoginViewDto;
import com.license.backend.domain.dto.UserViewDto;

import java.util.List;

public interface UserService {

    void create(UserCreateDto createDto);

    List<UserViewDto> getUsers();

    UserLoginViewDto login(UserLoginDto loginDto);

}
