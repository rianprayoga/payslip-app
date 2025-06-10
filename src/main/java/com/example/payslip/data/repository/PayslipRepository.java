package com.example.payslip.data.repository;

import com.example.payslip.data.entity.PayslipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PayslipRepository extends JpaRepository<PayslipEntity, UUID> {

    @Query(
            nativeQuery = true,
            value =
                    """
        SELECT * FROM payslips p WHERE p.payroll_id = :payrollId AND p.employee_id = :employeeId
    """)
    Optional<PayslipEntity> findBy(@Param("employeeId") UUID employeeId, @Param("payrollId") UUID payrollId);

    @Query(
            nativeQuery = true,
            value =
                    """
        SELECT * FROM payslips p WHERE p.payroll_id = :payrollId
    """)
    List<PayslipEntity> findBy(@Param("payrollId") UUID payrollId);

}
