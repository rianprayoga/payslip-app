package com.example.payslip.controller.attendances.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostAttendanceResponse {
    private AttendanceType type;
    private String message;
    private Long checkInTime;
    private Long checkOutTime;

    public static PostAttendanceResponse checkInResponse(String message, Long checkInTime) {
        return new PostAttendanceResponse(AttendanceType.CHECK_IN, message, checkInTime, null);
    }

    public static PostAttendanceResponse checkOutResponse(String message, Long checkOutTime) {
        return new PostAttendanceResponse(AttendanceType.CHECK_OUT, message, null, checkOutTime);
    }
}
