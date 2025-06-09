package com.example.payslip.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.UUID;

@Entity
@Table(name = "employee_reimbursements")
@Getter
@Setter
public class EmployeeReimbursementEntity {

    @Id
    private UUID id;

    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "amount")
    private BigInteger amount;

    @Column(name = "description")
    private String description;

    @Column(name = "submission_date")
    private Long submissionDate;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
