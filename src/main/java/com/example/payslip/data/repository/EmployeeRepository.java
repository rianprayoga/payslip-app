package com.example.payslip.data.repository;

import com.example.payslip.data.domain.Employee;
import com.example.payslip.data.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository  extends JpaRepository<EmployeeEntity, UUID> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM employees e WHERE e.username = :username
            """)
    Optional<EmployeeEntity> findEmployeeBy(@Param("username") String username);

    @Query(
            nativeQuery = true,
            value = """
            SELECT * FROM employees e WHERE e.username = :username
            """)
    Optional<EmployeeEntity> findByUsername(@Param("username") String username);

    @Query(nativeQuery = true,
    value = """
        SELECT
            e.id as employeeId,
            e.salary
        FROM employees e
        WHERE e.id IN (:ids)
    """)
    List<Employee> findBy(Collection<UUID> ids);
}
