package com.example.spring2024.todo.dto;

import lombok.Getter;

@Getter
public class TodoUpdateRequest {
    private Long memberId;
    private String updateContent;
}
