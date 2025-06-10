package com.example.payslip.data.repository;

import com.example.payslip.data.domain.Attendance;
import com.example.payslip.data.entity.EmployeeAttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<EmployeeAttendanceEntity, String> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM employee_attendances ea WHERE
        ea.employee_id = :employeeId AND ea.attendance_date = :attendanceDate
    """)
    Optional<EmployeeAttendanceEntity> findByEmployeeIdAndAttendanceDate(
            @Param("employeeId") UUID employeeId,
            @Param("attendanceDate") Long attendanceDate);

    @Query(
            nativeQuery = true,
            value =
                    """
            with attendance_count as (
                        select
                                ea.employee_id , count(ea.attendance_date) attendance_number
                from employee_attendances ea
                        where
                ea.attendance_date >= 1749513600000 and
                ea.attendance_date <= 1750031999000
                group by ea.employee_id
            )
        select
        ac.employee_id as employeeId,
        ac.attendance_number as attendanceNumber,
        ac.attendance_number * 8 * (e.salary/160) as salary
        from attendance_count ac left join employees e
        on ac.employee_id = e.id;
    """)
    List<Attendance> findAttendanceBy(@Param("startDate") Long startDate, @Param("endDate") Long endDate);
}
