package com.example.payslip.data.repository;

import com.example.payslip.data.domain.Reimbursement;
import com.example.payslip.data.entity.EmployeeReimbursementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReimburseRepository extends JpaRepository<EmployeeReimbursementEntity, UUID> {

    @Query(
            nativeQuery = true,
            value =
                    """
        select
        er.id,
        er.employee_id as employeeId,
        er.amount
        from employee_reimbursements er
            where
                        er.submission_date >= :startDate and
                        er.submission_date <= :endDate
        """)
    List<Reimbursement> findBy(@Param("startDate") Long startDate, @Param("endDate") Long endDate);
}
