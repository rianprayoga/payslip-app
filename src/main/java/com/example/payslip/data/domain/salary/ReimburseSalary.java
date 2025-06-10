package com.example.payslip.data.domain.salary;

import com.example.payslip.data.domain.Reimbursement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class ReimburseSalary {
    private UUID employeeId;
    private UUID reimbursementId;
    private BigDecimal amount;

    public ReimburseSalary(Reimbursement reimbursement) {
        this.employeeId = reimbursement.getEmployeeId();
        this.reimbursementId = reimbursement.getId();
        this.amount = reimbursement.getAmount();
    }
}
