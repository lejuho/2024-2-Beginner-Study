package com.example.todo_api.friendRequest.dto;

import com.example.todo_api.friendRequest.FriendRequest;
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

