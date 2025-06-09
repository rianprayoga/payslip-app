package com.example.payslip.controller.reimbusement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class PostReimburseRequest {
    @NotNull(message = "Value of amount can't be null.")
    @Positive(message = "Value of 'amount' must be bigger than 0.")
    private BigInteger amount;

    @NotEmpty(message = "Value of description can't be null.")
    private String description;
}
