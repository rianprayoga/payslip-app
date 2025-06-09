package com.example.payslip.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "employee_attendances")
@Getter
@Setter
public class EmployeeAttendanceEntity {

    @Id
    private UUID id;

    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "attendance_date")
    private Long attendanceDate;

    @Column(name = "check_in")
    private Long checkIn;

    @Column(name = "check_out")
    private Long checkOut;

    @Column(name = "created_on")
    private Long createdOn;

    @Column(name = "modified_on")
    private Long modifiedOn;
}
