package com.example.payslip.data.repository;

import com.example.payslip.data.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM payslip_admins pa
        WHERE pa.username = :username
    """)
    Optional<AdminEntity> findByUsername(@Param("username") String username);

}
