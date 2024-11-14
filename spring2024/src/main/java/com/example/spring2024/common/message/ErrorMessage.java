package com.example.spring2024.common.message;

public enum ErrorMessage {
    MEMBER_NOT_EXIST("존재하지 않는 멤버입니다."),
    TODO_NOT_EXIST("존재하지 않는 할 일입니다."),
    ONLY_GENERATED_TODO("할 일을 생성한 유저만 수정할 수 있습니다."),
    INVALID_LOGIN_CREDENTIAL("아이디 또는 비밀번호가 틀렸습니다."),
    ALREADY_FRIEND("이미 친구입니다."),
    ZERO_FRIEND("친구가 아직 없습니다."),
    NOT_FRIEND("친구가 아닙니다."),
    ALREADY_REQUESTED("이미 요청되었습니다."),
    REQUEST_NOT_EXIST("요청이 존재하지 않습니다."),
    NOT_SIGNED("로그인되지 않은 유저입니다.");

    private final String message;

    private ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
