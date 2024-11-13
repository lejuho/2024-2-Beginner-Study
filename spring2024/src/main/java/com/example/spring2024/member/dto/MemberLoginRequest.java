package com.example.spring2024.member.dto;

public class MemberLoginRequest {
    private String loginId;
    private String password;

    // Getters and Setters
    public String getLoginId() { return loginId; }
    public void setLoginId(String loginId) { this.loginId = loginId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
