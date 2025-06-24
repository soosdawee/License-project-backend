package com.license.backend.controller;

import com.license.backend.domain.dto.friend_request.FriendRequestDto;
import com.license.backend.domain.dto.friend_request.FriendRequestViewDto;
import com.license.backend.domain.dto.user.UserProfileViewDto;
import com.license.backend.service.FriendRequestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend_request")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService service;

    @PostMapping("{receiverId}")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public void sendRequest(@PathVariable Integer receiverId) {
        service.sendFriendRequest(receiverId);
    }

    @PutMapping("{requestId}")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public void modifyRequest(@PathVariable Integer requestId, @RequestBody FriendRequestDto dto) {
        service.respondToFriendRequest(requestId, dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityRequirement(name = "bearerAuth")
    public List<FriendRequestViewDto> getRequests() {
        return service.getPendingRequestsForCurrentUser();
    }

    @GetMapping("{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityRequirement(name = "bearerAuth")
    public List<UserProfileViewDto> getRequests(@PathVariable Integer userId) {
        return service.getFriends(userId);
    }

}
