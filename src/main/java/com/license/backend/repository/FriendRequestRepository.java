package com.license.backend.repository;

import com.license.backend.domain.constant.RequestStatus;
import com.license.backend.domain.model.FriendRequest;
import com.license.backend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    List<FriendRequest> findByReceiverAndStatus(User receiver, RequestStatus status);

    List<FriendRequest> findBySenderAndStatus(User sender, RequestStatus status);

    List<FriendRequest> findBySenderAndReceiver(User sender, User receiver);

}
