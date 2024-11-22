package com.example.todo_api.member.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class MemberUpdateRequest {
    @Length(min = 1, max = 20,message = "ID는 1자 이상, 20자 이하의 영문, 숫자, 특수문자의 조합입니다.")
    private String loginId;
    @Length(min = 1, max = 20,message = "비밀번호는 1자 이상, 20자 이하의 영문, 숫자, 특수문자의 조합입니다.")
    private String password;
}
