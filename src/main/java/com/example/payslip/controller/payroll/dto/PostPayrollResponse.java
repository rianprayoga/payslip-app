package com.example.payslip.controller.payroll.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostPayrollResponse {
    private String payrollId;
    private String message;
}
