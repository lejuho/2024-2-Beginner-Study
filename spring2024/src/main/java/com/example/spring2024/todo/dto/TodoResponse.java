package com.example.spring2024.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponse {
    private Long id;
    private String content;
    private boolean isChecked;
    private Long memberId;

    public TodoResponse(Long id, String content, boolean isChecked, Long memberId) {
        this.id = id;
        this.content = content;
        this.isChecked = isChecked;
        this.memberId = memberId;
    }
}
