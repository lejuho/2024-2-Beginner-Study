package com.example.spring2024.friend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class FriendshipCreateRequest {
    @NotNull(message = "유저 ID는 필수입니다.")
    @Positive(message = "유저 ID는 양수인 정수입니다.")
    private Long userId;
    @NotNull(message = "친구로 등록할 유저 ID는 필수입니다.")
    @Positive(message = "유저 ID는 양수인 정수입니다.")
    private Long friendId;
}
