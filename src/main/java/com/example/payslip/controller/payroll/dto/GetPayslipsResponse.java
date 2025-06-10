package com.example.payslip.controller.payroll.dto;

import com.example.payslip.data.domain.salary.DetailSalary;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
public class GetPayslipsResponse {

    private BigDecimal total;
    private List<EmployeeSalary> details;

    public GetPayslipsResponse(List<DetailSalary> details) {
        this.total = details.stream().map(DetailSalary::getTakeHomePay).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.details = details.stream()
                .map(detailSalary -> new EmployeeSalary(detailSalary.getEmployeeId(), detailSalary.getTakeHomePay()))
                .toList();
    }

    @AllArgsConstructor
    @Getter
    private class EmployeeSalary{
        private UUID employeeId;
        private BigDecimal takeHomePay;
    }
}
