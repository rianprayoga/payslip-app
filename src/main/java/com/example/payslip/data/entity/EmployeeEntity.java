package com.example.payslip.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Table(name = "employees")
@Entity
@Getter
@Setter
public class EmployeeEntity {

    @Id
    private UUID id;
    private String username;
    private String password;
    private BigDecimal salary;

}
