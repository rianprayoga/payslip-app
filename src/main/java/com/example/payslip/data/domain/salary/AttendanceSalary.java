package com.example.payslip.data.domain.salary;

import com.example.payslip.data.domain.Attendance;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class AttendanceSalary {
    private UUID employeeId;
    private int attendanceNumber;
    private BigDecimal salary;

    public AttendanceSalary(Attendance attendance) {
        this.employeeId = attendance.getEmployeeId();
        this.attendanceNumber = attendance.getAttendanceNumber();
        this.salary = attendance.getSalary();
    }
}
