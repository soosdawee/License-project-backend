package com.license.backend.domain.model;

import com.license.backend.domain.constant.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friendrequests_friendrequest_id_seq")
    @SequenceGenerator(name = "friendrequests_friendrequest_id_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

}
