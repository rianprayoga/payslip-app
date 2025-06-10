package com.example.payslip.data.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface Attendance {
    UUID getEmployeeId();
    Integer getAttendanceNumber();
    BigDecimal getSalary();
}
