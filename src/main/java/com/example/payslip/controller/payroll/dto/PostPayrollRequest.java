package com.example.payslip.controller.payroll.dto;


import com.example.payslip.errors.http.BadRequestException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class PostPayrollRequest {
    @NotEmpty(message = "Value of name can't be null.")
    private String name;
    @Positive(message = "Value of startDate must be bigger than 0.")
    private Long startDate;
    @Positive(message = "Value of endDate must be bigger than 0.")
    private Long endDate;

    public PostPayrollRequest(String name, Long startDate, Long endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;

        if (this.endDate < this.startDate){
            throw new BadRequestException("Value of endDate must be bigger than startDate.");
        }
    }
}
