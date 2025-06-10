package com.example.payslip.data.repository;

import com.example.payslip.data.entity.PayslipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PayslipRepository extends JpaRepository<PayslipEntity, UUID> {

}
