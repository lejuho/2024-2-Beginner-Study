package com.example.spring2024.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class TodoCreateRequest {
    @Length(max = 100)
    private String content;
    @NotNull
    private Long memberId;
}
