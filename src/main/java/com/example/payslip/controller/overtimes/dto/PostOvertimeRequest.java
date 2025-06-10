package com.example.payslip.controller.overtimes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostOvertimeRequest {

    @Positive(message = "Value of duration must be bigger than 0.")
    @Max(value = 3, message = "Value of duration must lower or equal to 3.")
    private int duration;

    @Positive(message = "Value of duration must be bigger than 0.")
    @NotNull
    private long overtimeDate;

}
