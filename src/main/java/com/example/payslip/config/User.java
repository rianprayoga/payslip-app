package com.example.payslip.config;

import lombok.Getter;

@Getter
public class User {
    private String id;
    private String username;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
