package com.license.backend.domain.dto.friend_request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
public class FriendRequestViewDto {

    Integer requestId;

    String firstname;

    String lastname;

    byte[] profilePicture;

}
