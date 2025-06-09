package com.example.payslip.data.repository;

import com.example.payslip.data.entity.EmployeeReimbursementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReimburseRepository extends JpaRepository<EmployeeReimbursementEntity, UUID> { }
