package com.example.payslip.data.repository;

import com.example.payslip.data.entity.EmployeeAttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
