package com.example.spring2024.friend.dto;

import lombok.Getter;

@Getter
public class FriendshipCreateRequest {
    private Long userId;
    private Long friendId;
}
