package com.example.payslip.data.domain.salary;

import com.example.payslip.data.domain.Overtime;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class OvertimeSalary {
    private UUID employeeId;
    private UUID overtimeId;
    private int duration;
    private BigDecimal salary;

    public OvertimeSalary(Overtime overtime) {
        this.employeeId = overtime.getEmployeeId();
        this.overtimeId = overtime.getId();
        this.duration = overtime.getDuration();
        this.salary = overtime.getSalary();
    }
}
