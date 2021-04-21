package com.revaturemax.dto;

import com.revaturemax.models.Employee;

public class LoginResponse {

    private String token;
    private Employee user;
    private Long userBatchId;

    public LoginResponse(String token, Employee user, Long userBatchId) {
        this.token = token;
        this.user = user;
        this.userBatchId = userBatchId;
    }

    public String getToken() {
        return token;
    }

    public Employee getUser() {
        return user;
    }

    public Long getUserBatchId() {
        return userBatchId;
    }

}
