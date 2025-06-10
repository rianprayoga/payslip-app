package com.example.payslip.data.domain.salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Setter
@NoArgsConstructor
@Getter
public class DetailSalary {
    private UUID employeeId;
    private BigDecimal takeHomePay;
    private AttendanceSalary attendanceSalary;
    private List<OvertimeSalary> overtimeSalaries;
    private List<ReimburseSalary> reimbursements;

}
