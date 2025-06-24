package com.license.backend.repository;

import com.license.backend.domain.constant.RequestStatus;
import com.license.backend.domain.model.FriendRequest;
import com.license.backend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    List<FriendRequest> findByReceiverAndStatus(User receiver, RequestStatus status);

    List<FriendRequest> findBySenderAndStatus(User sender, RequestStatus status);

    List<FriendRequest> findBySenderAndReceiver(User sender, User receiver);

    @Query("SELECT fr.receiver FROM FriendRequest fr WHERE fr.sender = :user AND fr.status = 'ACCEPTED'")
    List<User> findAcceptedFriendsSentBy(@Param("user") User user);

    @Query("SELECT fr.sender FROM FriendRequest fr WHERE fr.receiver = :user AND fr.status = 'ACCEPTED'")
    List<User> findAcceptedFriendsReceivedBy(@Param("user") User user);


}
