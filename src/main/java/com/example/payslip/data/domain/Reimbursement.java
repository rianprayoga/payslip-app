package com.example.payslip.data.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface Reimbursement {
    UUID getId();
    UUID getEmployeeId();
    BigDecimal getAmount();
}
