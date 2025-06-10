package com.example.payslip.data.entity;

import com.example.payslip.data.domain.salary.DetailSalary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payslips")
@Getter
@Setter
public class PayslipEntity {
    @Id
    private UUID id;

    @Column(name = "payroll_id")
    private UUID payrollId;

    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "takehome_pay")
    private BigDecimal takeHomePay;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detail")
    private DetailSalary detailSalary;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
