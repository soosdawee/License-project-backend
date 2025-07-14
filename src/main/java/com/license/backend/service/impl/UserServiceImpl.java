package com.license.backend.service.impl;

import com.license.backend.config.TokenProvider;
import com.license.backend.domain.dto.user.*;
import com.license.backend.domain.dto.visualization.VisualizationReducedViewDto;
import com.license.backend.domain.mapper.UserMapper;
import com.license.backend.domain.mapper.VisualizationMapper;
import com.license.backend.domain.model.User;
import com.license.backend.domain.model.Visualization;
import com.license.backend.exception.DataMismatchException;
import com.license.backend.exception.FailedLoginException;
import com.license.backend.repository.UserRepository;
import com.license.backend.repository.VisualizationRepository;
import com.license.backend.service.UserService;
import com.license.backend.util.ContextUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final VisualizationRepository visualizationRepository;

    private final UserMapper mapper;

    private final VisualizationMapper visualizationMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailUsername;

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
            throw new FailedLoginException("There is no user with this email!");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getUserPassword());

        try {
            var authUser = authenticationManager.authenticate(usernamePassword);
            var accessToken = tokenProvider.generateAccessToken((User) authUser.getPrincipal());
            return new UserLoginViewDto(user.getUserId(), user.getEmail(), user.getUserType(), accessToken);
        } catch (AuthenticationException authenticationException) {
            throw new FailedLoginException("Email or password incorrect!");
        }
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

    @Override
    @Transactional(readOnly = true)
    public UserProfileViewDto getUser(Integer userId) {
        Optional<User> user = repository.findById(userId);
        if (user.isPresent()) {
            return mapper.toProfileViewDto(user.get());
        } else {
            System.out.println("No user with this id exception placeholder!");
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<VisualizationReducedViewDto> getLiked(Integer userId) {
        Set<Visualization> liked = repository.findLikedVisualizationsByUserId(userId);
        return visualizationMapper.toReducedViewDto(liked);
    }

    @Override
    @Transactional
    public void updateProfilePicture(MultipartFile file) throws IOException {
        ContextUtil.getAuthenticatedUser().setProfilePicture(file.getBytes());
        repository.save(ContextUtil.getAuthenticatedUser());
    }

    @Override
    @Transactional
    public void update(UserUpdateDto updateDto) {
        User user = ContextUtil.getAuthenticatedUser();
        user.setFirstname(updateDto.getFirstname());
        user.setLastname(updateDto.getLastname());
        user.setDescription(updateDto.getDescription());
        repository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(UserPasswordDto updateDto) {
        User user = ContextUtil.getAuthenticatedUser();
        if (passwordEncoder.matches(updateDto.getOldPassword(), user.getUserPassword())
            && updateDto.getNewPassword().equals(updateDto.getNewPasswordRepeated())) {
            user.setUserPassword(passwordEncoder.encode(updateDto.getNewPassword()));
            repository.save(user);
        } else {
            throw new DataMismatchException("Incorrect old password given!");
        }
    }

    @Override
    @Transactional
    public void softDelete() {
        User user = ContextUtil.getAuthenticatedUser();
        user.setIsActive(false);
        repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Integer userId) {
        repository.deleteById(userId);
    }

    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException, jakarta.mail.MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(mailUsername, "Datavue");
        helper.setTo(recipientEmail);

        String subject = "Kids, do not try this at home!";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public void processForgotPassword(String email) throws MessagingException, UnsupportedEncodingException, jakarta.mail.MessagingException {
        User user = repository.findUserByEmail(email);
        String token = RandomStringUtils.randomAlphanumeric(30);
        user.setResetPasswordToken(token);
        repository.save(user);
        String resetPasswordLink = "http://localhost:3000/reset-password/" + token;
        sendEmail(email, resetPasswordLink);
    }

    @Override
    @Transactional
    public void processResetPassword(String token, String password) {
        User user = repository.findUserByResetPasswordToken(token);
        user.setUserPassword(passwordEncoder.encode(password));
        user.setResetPasswordToken(null);
        repository.save(user);
    }

}
