package com.example.payslip.data.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface Overtime {
    UUID getId();
    UUID getEmployeeId();
    int getDuration();
    BigDecimal getSalary();
}
