package com.license.backend.domain.dto.friend_request;

import com.license.backend.domain.constant.RequestStatus;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class FriendRequestDto {

    RequestStatus status;

}
