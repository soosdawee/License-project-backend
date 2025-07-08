package com.license.backend.service;

import com.license.backend.domain.dto.user.UserCreateDto;
import com.license.backend.domain.dto.user.UserPasswordDto;
import com.license.backend.domain.dto.user.UserProfileViewDto;
import com.license.backend.domain.dto.user.UserUpdateDto;
import com.license.backend.domain.mapper.UserMapper;
import com.license.backend.domain.model.User;
import com.license.backend.exception.DataMismatchException;
import com.license.backend.repository.UserRepository;
import com.license.backend.service.impl.UserServiceImpl;
import com.license.backend.util.ContextUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private MockedStatic<ContextUtil> contextUtilMock;

    @BeforeAll
    public static void beforeAll() {
        MockitoAnnotations.openMocks(UserServiceUnitTest.class);
    }

    @BeforeEach
    void setUp() {
        contextUtilMock = mockStatic(ContextUtil.class);
    }

    @AfterEach
    void tearDown() {
        contextUtilMock.close();
    }

    @Test
    public void whenUserCreated_thenFlowAsExpected() {
        String email = "testemail02@gmail.com";
        String rawPassword = "string";
        String encodedPassword = "encodedString";

        UserCreateDto createDto = UserCreateDto.builder()
                .email(email)
                .firstname("Johny")
                .lastname("test")
                .userPassword(rawPassword)
                .build();

        User mappedUser = new User();
        mappedUser.setEmail(email);
        mappedUser.setUserPassword(rawPassword);

        given(userMapper.toEntity(createDto)).willReturn(mappedUser);
        given(passwordEncoder.encode(rawPassword)).willReturn(encodedPassword);

        userService.create(createDto);

        verify(userRepository).save(mappedUser);
    }

    @Test
    public void whenUserWithIdExists_thenUserIsReturned() {
        Integer userId = 1;
        User existingUser = new User();
        existingUser.setUserId(userId);
        existingUser.setFirstname("John");
        existingUser.setLastname("Test");

        UserProfileViewDto expectedDto = UserProfileViewDto.builder()
                .userId(userId)
                .firstname("John")
                .lastname("Test")
                .build();

        given(userRepository.findById(userId)).willReturn(Optional.of(existingUser));
        given(userMapper.toProfileViewDto(existingUser)).willReturn(expectedDto);

        var result = userService.getUser(userId);

        verify(userRepository).findById(userId);
        verify(userMapper).toProfileViewDto(existingUser);
        assert result != null;
        assert result.getUserId().equals(userId);
        assert result.getFirstname().equals("John");
    }

    @Test
    public void whenUserWithIdDoesNotExists_thenNullIsReturned() {
        Integer userId = 6969;

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        var result = userService.getUser(userId);

        verify(userRepository).findById(userId);
        verifyNoInteractions(userMapper);

        assert result == null;
    }

    @Test
    public void whenUserModifiesPersonalInfo_thenInfoIsModified() {
        String modifiedFirst = "Modif";
        String modifiedLast = "Ied";
        String modifiedDescription = "This description was modified";
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .firstname(modifiedFirst)
                .lastname(modifiedLast)
                .description(modifiedDescription)
                .build();

        User user = new User();
        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(user);

        userService.update(userUpdateDto);

        assert user.getFirstname().equals(modifiedFirst);
        assert user.getLastname().equals(modifiedLast);
        assert user.getDescription().equals(modifiedDescription);

        verify(userRepository).save(user);
    }

    @Test
    public void whenUserModifiesPassword_thenPasswordIsModified() {
        String oldPassword = "oldP";
        String newPassword = "newP";
        String newPasswordRepeated = "newP";
        String oldPasswordEncoded = "Encoded100%";
        String newPasswordEncoded = "Encoded110%";
        UserPasswordDto userPasswordDto = UserPasswordDto.builder()
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .newPasswordRepeated(newPasswordRepeated)
                .build();

        User user = new User();
        user.setUserPassword(oldPasswordEncoded);
        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(user);
        when(passwordEncoder.matches(oldPassword, oldPasswordEncoded)).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(newPasswordEncoded);

        userService.updatePassword(userPasswordDto);

        assert user.getUserPassword().equals(newPasswordEncoded);
        verify(userRepository).save(user);
    }

    @Test
    public void whenUserModifiesPasswordInAWrongWay_thenPasswordIsNotModified() {
        String oldPassword = "oldP";
        String newPassword = "newP";
        String newPasswordRepeated = "newP";
        String oldPasswordEncoded = "Encoded100%";
        String newPasswordEncoded = "Encoded110%";
        UserPasswordDto userPasswordDto = UserPasswordDto.builder()
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .newPasswordRepeated(newPasswordRepeated)
                .build();

        User user = new User();
        user.setUserPassword(oldPasswordEncoded);
        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(user);

        when(passwordEncoder.matches(oldPassword, oldPasswordEncoded)).thenReturn(false);

        assertThrows(DataMismatchException.class, () -> userService.updatePassword(userPasswordDto));
        assert user.getUserPassword().equals(oldPasswordEncoded);
        verify(userRepository, never()).save(any());
    }

    @Test
    public void whenUserDeletesProfile_thenIsNoLongerActive() {
        User user = new User();
        user.setIsActive(true);
        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(user);

        userService.softDelete();

        assert !user.getIsActive();
        verify(userRepository).save(user);
    }

}
