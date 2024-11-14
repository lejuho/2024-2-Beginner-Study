package com.example.spring2024.friendRequest.dto;

import com.example.spring2024.friendRequest.FriendRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FriendRequestResponse {
    private Long id;
    private Long requesterId;
    private Long receiverId;
    private FriendRequest.Status status;
    private boolean isFriend;

    public FriendRequestResponse(Long id, Long requesterId, Long receiverId, FriendRequest.Status status, boolean isFriend) {
        this.id = id;
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.status = status;
        this.isFriend = isFriend;
    }
}

