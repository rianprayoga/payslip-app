package com.example.payslip.controller.attendances.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class PostAttendanceRequest {

    @NotNull(message = "Value of date can't be null.")
    private Long date;
    @NotNull(message = "Value of type can't be null.")
    private AttendanceType type;

}
