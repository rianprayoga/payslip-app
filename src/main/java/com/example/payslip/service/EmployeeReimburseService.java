package com.example.payslip.service;

import com.example.payslip.config.User;
import com.example.payslip.controller.reimbusement.dto.PostReimburseRequest;
import com.example.payslip.controller.reimbusement.dto.PostReimburseResponse;
import com.example.payslip.data.entity.EmployeeReimbursementEntity;
import com.example.payslip.data.repository.PayrollRepository;
import com.example.payslip.data.repository.ReimburseRepository;
import com.example.payslip.errors.http.BadRequestException;
import com.example.payslip.utilities.DateHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeReimburseService {

    private final ReimburseRepository reimburseRepository;
    private final PayrollRepository payrollRepository;
    private final DateHelper dateHelper;

    public PostReimburseResponse postReimburse(User user, PostReimburseRequest request) {

        Optional<UUID> payroll = payrollRepository.findCurrentPayrollBy(System.currentTimeMillis());
        if (payroll.isPresent()){
            throw new BadRequestException("Current payroll already locked.");
        }

        EmployeeReimbursementEntity reimbursement = new EmployeeReimbursementEntity();
        reimbursement.setId(UUID.randomUUID());
        reimbursement.setEmployeeId(user.getId());
        reimbursement.setAmount(request.getAmount());
        reimbursement.setDescription(request.getDescription());
        reimbursement.setSubmissionDate(dateHelper.toEarlyNight(System.currentTimeMillis()));

        long timeMillis = System.currentTimeMillis();
        reimbursement.setCreatedAt(timeMillis);
        reimbursement.setUpdatedAt(timeMillis);

        reimburseRepository.save(reimbursement);

        return new PostReimburseResponse(reimbursement.getId().toString(), "Reimbursement added.");
    }
}
