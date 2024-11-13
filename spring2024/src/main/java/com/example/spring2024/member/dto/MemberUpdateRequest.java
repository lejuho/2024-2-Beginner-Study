package com.example.spring2024.member.dto;

import lombok.Getter;

@Getter
public class MemberUpdateRequest {
    private String loginId;
    private String password;
}
