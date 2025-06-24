package com.license.backend.service.impl;

import com.license.backend.config.TokenProvider;
import com.license.backend.domain.dto.user.UserCreateDto;
import com.license.backend.domain.dto.user.UserLoginDto;
import com.license.backend.domain.dto.user.UserLoginViewDto;
import com.license.backend.domain.dto.user.UserViewDto;
import com.license.backend.domain.mapper.UserMapper;
import com.license.backend.domain.model.User;
import com.license.backend.domain.model.Visualization;
import com.license.backend.repository.UserRepository;
import com.license.backend.repository.VisualizationRepository;
import com.license.backend.service.UserService;
import com.license.backend.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final VisualizationRepository visualizationRepository;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    @Override
    @Transactional
    public void create(UserCreateDto createDto) {
        User user = mapper.toEntity(createDto);
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserViewDto> getUsers() {
        List<User> users = repository.findAll();
        return mapper.toViewDtos(users);
    }

    @Override
    @Transactional(readOnly = true)
    public UserLoginViewDto login(UserLoginDto loginDto) {
        User user = repository.findByEmail(loginDto.getEmail());

        if (user == null || !user.getIsActive()) {
            System.out.println("User not found exception placeholder\n");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getUserPassword());

        try {
            var authUser = authenticationManager.authenticate(usernamePassword);
            var accessToken = tokenProvider.generateAccessToken((User) authUser.getPrincipal());
            return new UserLoginViewDto(user.getUserId(), user.getEmail(), user.getUserType(), accessToken);
        } catch (AuthenticationException authenticationException) {
            System.out.println("Login fail exception placeholder\n");
        }

        return null;
    }

    @Override
    @Transactional
    public void likeVisualization(Integer visualizationId) {
        Visualization visualization = visualizationRepository.findById(visualizationId)
                .orElseThrow(() -> new RuntimeException("Visualization not found"));
        String email = ContextUtil.getAuthenticatedUser().getEmail();
        User user = repository.findByEmail(email);
        if (user.getLikedVisualizations().contains(visualization)) {
            user.getLikedVisualizations().remove(visualization);
        } else {
            user.getLikedVisualizations().add(visualization);
        }
        repository.save(user);
    }

}
