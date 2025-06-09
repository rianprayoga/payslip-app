package com.example.payslip.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "employee_overtimes")
@Getter
@Setter
public class EmployeeOvertimeEntity {
    @Id
    private UUID id;

    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "duration")
    private int duration;

    @Column(name = "submission_date")
    private Long submissionDate;

    @Column(name = "overtime_date")
    private Long overtimeDate;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
