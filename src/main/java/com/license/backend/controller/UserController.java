package com.license.backend.controller;

import com.license.backend.domain.dto.user.*;
import com.license.backend.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserCreateDto userCreateDto) {
        service.create(userCreateDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserLoginViewDto login(@RequestBody UserLoginDto userLoginDto) {
        return service.login(userLoginDto);
    }

    @PostMapping("{visualizationId}/like")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public void likeVisualization(@PathVariable Integer visualizationId){
        service.likeVisualization(visualizationId);
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public List<UserViewDto> getUsers() {
        return service.getUsers();
    }

    @GetMapping("{userId}")
    @SecurityRequirement(name = "bearerAuth")
    public UserProfileViewDto getUser(@PathVariable Integer userId) {
        return service.getUser(userId);
    }

}
