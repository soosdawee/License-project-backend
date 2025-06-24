package com.license.backend.domain.mapper;

import com.license.backend.domain.dto.user.UserCreateDto;
import com.license.backend.domain.dto.user.UserProfileViewDto;
import com.license.backend.domain.dto.user.UserViewDto;
import com.license.backend.domain.model.User;
import com.license.backend.domain.model.Visualization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.generated", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "userType", constant = "REGULAR")
    @Mapping(target = "description", constant = "")
    User toEntity(UserCreateDto userCreateDto);

    List<UserViewDto> toViewDtos(List<User> users);

    @Mapping(source = "visualizations", target = "sharedVisualizationIds")
    @Mapping(source = "likedVisualizations", target = "likedVisualizationIds")
    UserProfileViewDto toProfileViewDto(User user);

    default List<Integer> mapVisualizationsToIds(Collection<Visualization> visualizations) {
        if (visualizations == null) return null;
        return visualizations.stream()
                .filter(Visualization::getIsShared)
                .map(Visualization::getVisualizationId)
                .toList();
    }

}
