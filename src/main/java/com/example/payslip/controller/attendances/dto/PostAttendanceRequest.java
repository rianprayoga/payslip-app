package com.example.payslip.controller.attendances.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class PostAttendanceRequest {

    @Positive(message = "Value of date must be bigger than 0.")
    @NotNull(message = "Value of date can't be null.")
    private Long date;
    @NotNull(message = "Value of type can't be null.")
    private AttendanceType type;

}
