package com.license.backend.domain.mapper;

import com.license.backend.domain.dto.user.UserCreateDto;
import com.license.backend.domain.dto.user.UserViewDto;
import com.license.backend.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.generated", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "userType", constant = "REGULAR")
    User toEntity(UserCreateDto userCreateDto);

    List<UserViewDto> toViewDtos(List<User> users);

}
