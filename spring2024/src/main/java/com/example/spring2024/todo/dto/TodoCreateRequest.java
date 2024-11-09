package com.example.spring2024.todo.dto;

import lombok.Getter;

@Getter
public class TodoCreateRequest {
    private String content;
    private Long memberId;
}
