package com.license.backend.controller;

import com.license.backend.domain.dto.user.*;
import com.license.backend.domain.dto.visualization.VisualizationReducedViewDto;
import com.license.backend.domain.dto.visualization.VisualizationViewDto;
import com.license.backend.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

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

    @GetMapping("{userId}/liked")
    @SecurityRequirement(name = "bearerAuth")
    public Set<VisualizationReducedViewDto> getLikedVisualizations(@PathVariable Integer userId) {
        return service.getLiked(userId);
    }

    @PutMapping("/profile_picture")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityRequirement(name = "bearerAuth")
    public void updateProfilePicture(@RequestParam("file") MultipartFile file) throws IOException {
        service.updateProfilePicture(file);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityRequirement(name = "bearerAuth")
    public void updateProfilePicture(@RequestBody UserUpdateDto dto) {
        service.update(dto);
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityRequirement(name = "bearerAuth")
    public void updateProfilePicture(@RequestBody UserPasswordDto dto) {
        service.updatePassword(dto);
    }

    @PutMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityRequirement(name = "bearerAuth")
    public void softDelete() {
        service.softDelete();
    }

    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityRequirement(name = "bearerAuth")
    public void delete(@PathVariable Integer userId) {
        service.delete(userId);
    }

    @PostMapping("forgot_password")
    public void forgotPassword(@RequestParam String email) throws MessagingException, UnsupportedEncodingException {
        service.processForgotPassword(email);
    }

    @PostMapping("reset_password")
    public void processResetPassword(@RequestParam String token, @RequestParam String password) {
        service.processResetPassword(token, password);
    }

}
