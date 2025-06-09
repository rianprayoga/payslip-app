package com.example.payslip.data.repository;

import com.example.payslip.data.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository  extends JpaRepository<EmployeeEntity,String> {

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
}
