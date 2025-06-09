package com.example.payslip.service;

import com.example.payslip.config.User;
import com.example.payslip.controller.reimbusement.dto.PostReimburseRequest;
import com.example.payslip.controller.reimbusement.dto.PostReimburseResponse;
import com.example.payslip.data.entity.EmployeeReimbursementEntity;
import com.example.payslip.data.repository.ReimburseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeReimburseService {

    private final ReimburseRepository reimburseRepository;

    public PostReimburseResponse postReimburse(User user, PostReimburseRequest request) {

        EmployeeReimbursementEntity reimbursement = new EmployeeReimbursementEntity();
        reimbursement.setId(UUID.randomUUID());
        reimbursement.setEmployeeId(user.getId());
        reimbursement.setAmount(request.getAmount());
        reimbursement.setDescription(request.getDescription());

        long timeMillis = System.currentTimeMillis();
        reimbursement.setCreatedAt(timeMillis);
        reimbursement.setUpdatedAt(timeMillis);

        reimburseRepository.save(reimbursement);

        return new PostReimburseResponse(reimbursement.getId().toString(), "Reimbursement added.");
    }
}
