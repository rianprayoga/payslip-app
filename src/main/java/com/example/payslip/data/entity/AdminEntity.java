package com.example.payslip.data.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.UUID;

@Table(name = "payslip_admins")
@Entity
@Getter
public class AdminEntity {

    @Id
    private UUID id;
    private String username;
    private String password;
}
