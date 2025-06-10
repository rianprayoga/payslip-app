package com.example.payslip.data.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface Employee {
    UUID getEmployeeId();
    BigDecimal getSalary();

    default BigDecimal getSalaryPerHour(){
        return this.getSalary().divide(BigDecimal.valueOf(160L));
    }

}
