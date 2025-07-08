package com.license.backend.service;

import com.license.backend.domain.constant.RequestStatus;
import com.license.backend.domain.dto.friend_request.FriendRequestDto;
import com.license.backend.domain.dto.friend_request.FriendRequestViewDto;
import com.license.backend.domain.dto.user.UserProfileViewDto;
import com.license.backend.domain.mapper.UserMapper;
import com.license.backend.domain.model.FriendRequest;
import com.license.backend.domain.model.User;
import com.license.backend.repository.FriendRequestRepository;
import com.license.backend.repository.UserRepository;
import com.license.backend.service.impl.FriendRequestServiceImpl;
import com.license.backend.util.ContextUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FriendRequestServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private FriendRequestServiceImpl friendRequestService;

    private MockedStatic<ContextUtil> contextUtilMock;

    private User sender;
    private User receiver;

    @BeforeEach
    void setup() {
        contextUtilMock = mockStatic(ContextUtil.class);

        sender = new User();
        sender.setUserId(1);

        receiver = new User();
        receiver.setUserId(2);
    }

    @AfterEach
    void tearDown() {
        contextUtilMock.close();
    }

    @Test
    public void whenFriendRequestSent_thenFlowAsExpected() {
        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(sender);
        when(userRepository.findById(2)).thenReturn(Optional.of(receiver));
        when(friendRequestRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(List.of());

        friendRequestService.sendFriendRequest(2);

        verify(friendRequestRepository).save(any(FriendRequest.class));
    }

    @Test
    public void whenFriendRequestSentToSelf_exceptionThrown() {
        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(sender);
        when(userRepository.findById(1)).thenReturn(Optional.of(sender));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> friendRequestService.sendFriendRequest(1));
        assertEquals("Cannot send friend request to yourself", ex.getMessage());
    }

    @Test
    public void whenFriendRequestSentAlreadyExists_exceptionThrown() {
        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(sender);
        when(userRepository.findById(2)).thenReturn(Optional.of(receiver));

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setStatus(RequestStatus.PENDING);
        when(friendRequestRepository.findBySenderAndReceiver(sender, receiver))
                .thenReturn(List.of(friendRequest));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> friendRequestService.sendFriendRequest(2));
        assertEquals("Friend request already sent or already friends", ex.getMessage());
    }

    @Test
    public void whenFriendRequestAccepted_thenFlowAsExpected() {
        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(receiver);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setStatus(RequestStatus.PENDING);
        friendRequest.setId(1);
        friendRequest.setReceiver(receiver);

        when(friendRequestRepository.findById(1)).thenReturn(Optional.of(friendRequest));

        FriendRequestDto dto = new FriendRequestDto();
        dto.setStatus(RequestStatus.ACCEPTED);

        friendRequestService.respondToFriendRequest(1, dto);

        assertEquals(RequestStatus.ACCEPTED, friendRequest.getStatus());
        verify(friendRequestRepository).save(friendRequest);
    }

    @Test
    public void whenFriendRequestAcceptedNotByReceiver_exceptionThrown() {
        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(sender);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setStatus(RequestStatus.PENDING);
        friendRequest.setId(1);
        friendRequest.setReceiver(receiver);

        when(friendRequestRepository.findById(1)).thenReturn(Optional.of(friendRequest));

        FriendRequestDto dto = new FriendRequestDto();
        dto.setStatus(RequestStatus.ACCEPTED);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> friendRequestService.respondToFriendRequest(1, dto));
        assertEquals("You are not authorized to respond to this request", ex.getMessage());
    }

    @Test
    public void whenPendingRequestsAreFetched_thenFlowAsExpected() {
        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(receiver);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setStatus(RequestStatus.PENDING);
        friendRequest.setId(1);
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);

        when(friendRequestRepository.findByReceiverAndStatus(receiver, RequestStatus.PENDING))
                .thenReturn(List.of(friendRequest));

        List<FriendRequestViewDto> result = friendRequestService.getPendingRequestsForCurrentUser();

        assertEquals(1, result.size());
        assertEquals(sender.getFirstname(), result.get(0).getFirstname());
    }

    @Test
    void whenFriendsFetched_thenFlowAsExpected() {
        User user = new User();
        user.setUserId(3);

        User friend1 = new User();
        friend1.setUserId(4);

        User friend2 = new User();
        friend2.setUserId(5);

        when(userRepository.findById(3)).thenReturn(Optional.of(user));
        when(friendRequestRepository.findAcceptedFriendsSentBy(user)).thenReturn(List.of(friend1));
        when(friendRequestRepository.findAcceptedFriendsReceivedBy(user)).thenReturn(List.of(friend2));

        UserProfileViewDto dto1 = UserProfileViewDto.builder()
                .userId(4)
                .build();
        UserProfileViewDto dto2 = UserProfileViewDto.builder()
                .userId(5)
                .build();

        when(userMapper.toProfileViewDto(friend1)).thenReturn(dto1);
        when(userMapper.toProfileViewDto(friend2)).thenReturn(dto2);

        List<UserProfileViewDto> friends = friendRequestService.getFriends(3);

        assertEquals(2, friends.size());
        assertTrue(friends.stream().anyMatch(f -> f.getUserId().equals(4)));
        assertTrue(friends.stream().anyMatch(f -> f.getUserId().equals(5)));
    }

}
