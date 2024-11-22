package com.example.todo_api.friend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendshipResponse {
    private Long friendshipId;
    private Long userId;
    private Long friendId;

    public FriendshipResponse(Long friendshipId,Long userId, Long friendId) {
        this.friendshipId = friendshipId;
        this.userId = userId;
        this.friendId = friendId;
    }
}
