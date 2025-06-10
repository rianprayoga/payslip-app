package com.example.payslip.data.repository;

import com.example.payslip.data.domain.Overtime;
import com.example.payslip.data.entity.EmployeeOvertimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OvertimeRepository extends JpaRepository<EmployeeOvertimeEntity, UUID> {

    @Query(
            nativeQuery = true,
            value =
                    """
        SELECT * FROM employee_overtimes eo WHERE eo.employee_id = :employeeId
        AND eo.overtime_date = :overtimeDate
    """)
    List<EmployeeOvertimeEntity> findByEmployeeIdAndOvertimeDate(
            @Param("employeeId") UUID employeeId, @Param("overtimeDate") Long overtimeDate);

    @Query(
            nativeQuery = true,
            value =
                    """
    select
        eo.id ,
        eo.employee_id employeeId,
        eo.duration ,
        eo.duration * (e.salary/160) as salary
    from employee_overtimes eo left join employees e
    on eo.employee_id = e.id
            where
        eo.overtime_date >= :startDate and
        eo.overtime_date <= :endDate
    """)
    List<Overtime> findBy(@Param("startDate") Long startDate, @Param("endDate") Long endDate);
}
