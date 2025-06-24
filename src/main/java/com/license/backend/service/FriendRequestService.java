package com.license.backend.service;

import com.license.backend.domain.dto.friend_request.FriendRequestDto;
import com.license.backend.domain.dto.friend_request.FriendRequestViewDto;
import com.license.backend.domain.dto.user.UserProfileViewDto;

import java.util.List;

public interface FriendRequestService {

    void sendFriendRequest(Integer receiverId);

    void respondToFriendRequest(Integer requestId, FriendRequestDto dto);

    List<FriendRequestViewDto> getPendingRequestsForCurrentUser();

    List<UserProfileViewDto> getFriends(Integer userId);

}
