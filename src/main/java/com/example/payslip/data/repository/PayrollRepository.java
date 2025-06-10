package com.example.payslip.data.repository;

import com.example.payslip.data.entity.PayrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PayrollRepository extends JpaRepository<PayrollEntity, UUID> {

    @Query(
            nativeQuery = true,
            value =
                    """
        SELECT * FROM payrolls p
        WHERE
            :start < p.end_date AND
            :end > p.start_date
    """)
    Optional<PayrollEntity> findBy(@Param("start") Long startDate, @Param("end") Long endDate);

    @Query(nativeQuery = true, value = """
    SELECT
        p.id
    FROM payrolls p
    WHERE
        p.start_date <= :currentDate and
        p.end_date >= :currentDate
    """)
    Optional<UUID> findCurrentPayrollBy(@Param("currentDate") Long currentDate);
}
