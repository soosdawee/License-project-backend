package com.license.backend.service.impl;

import com.license.backend.domain.constant.RequestStatus;
import com.license.backend.domain.dto.friend_request.FriendRequestDto;
import com.license.backend.domain.dto.friend_request.FriendRequestViewDto;
import com.license.backend.domain.dto.friend_request.FriendshipDto;
import com.license.backend.domain.dto.user.UserProfileViewDto;
import com.license.backend.domain.mapper.UserMapper;
import com.license.backend.domain.model.FriendRequest;
import com.license.backend.domain.model.User;
import com.license.backend.repository.FriendRequestRepository;
import com.license.backend.repository.UserRepository;
import com.license.backend.service.FriendRequestService;
import com.license.backend.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private final UserRepository userRepository;

    private final FriendRequestRepository friendRequestRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public void sendFriendRequest(Integer receiverId) {
        User sender = ContextUtil.getAuthenticatedUser();
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getUserId().equals(receiver.getUserId())) {
            throw new RuntimeException("Cannot send friend request to yourself");
        }

        boolean exists = friendRequestRepository
                .findBySenderAndReceiver(sender, receiver)
                .stream()
                .anyMatch(req -> req.getStatus() == RequestStatus.PENDING || req.getStatus() == RequestStatus.ACCEPTED);

        if (exists) {
            throw new RuntimeException("Friend request already sent or already friends");
        }

        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(RequestStatus.PENDING);
        request.setSentAt(LocalDateTime.now());

        friendRequestRepository.save(request);
    }

    @Override
    @Transactional
    public void respondToFriendRequest(Integer requestId, FriendRequestDto dto) {
        System.out.println(dto.getStatus().toString());
        if (dto.getStatus() != RequestStatus.ACCEPTED && dto.getStatus() != RequestStatus.REJECTED) {
            throw new IllegalArgumentException("Invalid status update");
        }

        User currentUser = ContextUtil.getAuthenticatedUser();

        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        if (!request.getReceiver().getUserId().equals(currentUser.getUserId())) {
            throw new RuntimeException("You are not authorized to respond to this request");
        }

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("Request already responded to");
        }

        request.setStatus(dto.getStatus());
        friendRequestRepository.save(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendRequestViewDto> getPendingRequestsForCurrentUser() {
        User currentUser = ContextUtil.getAuthenticatedUser();
        List<FriendRequest> pendingRequests = friendRequestRepository
                .findByReceiverAndStatus(currentUser, RequestStatus.PENDING);

        return pendingRequests.stream()
                .map(req -> new FriendRequestViewDto(
                        req.getId(),
                        req.getSender().getFirstname(),
                        req.getSender().getLastname()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfileViewDto> getFriends(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> sent = friendRequestRepository.findAcceptedFriendsSentBy(user);
        List<User> received = friendRequestRepository.findAcceptedFriendsReceivedBy(user);

        return Stream.concat(sent.stream(), received.stream())
                .distinct()
                .map(userMapper::toProfileViewDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FriendshipDto areWeFriends(Integer userId) {
        Boolean isFriend = getFriends(ContextUtil.getAuthenticatedUser().getUserId()).stream()
                .map(UserProfileViewDto::getUserId)
                .anyMatch(id -> id.equals(userId));

        return new FriendshipDto(isFriend);
    }

}

