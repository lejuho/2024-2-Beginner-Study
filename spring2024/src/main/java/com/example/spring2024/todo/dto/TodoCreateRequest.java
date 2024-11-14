package com.example.spring2024.todo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class TodoCreateRequest {
    @Length(max = 200,message = "할 일 내용은 200자를 넘을 수 없습니다.")
    private String content;
    @NotNull(message = "유저 ID는 필수입니다.")
    @Positive(message = "유저 ID는 양수인 정수입니다.")
    private Long memberId;
}
