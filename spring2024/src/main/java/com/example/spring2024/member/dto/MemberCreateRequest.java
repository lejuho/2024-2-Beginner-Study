package com.example.spring2024.member.dto;

import lombok.Getter;

@Getter
public class MemberCreateRequest {
    private String loginId;
    private String password;
}
