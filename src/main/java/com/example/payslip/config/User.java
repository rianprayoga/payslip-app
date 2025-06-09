package com.example.payslip.config;

import lombok.Getter;

import java.util.UUID;

@Getter
public class User {
    private UUID id;
    private String username;

    public User(UUID id, String username) {
        this.id = id;
        this.username = username;
    }
}
