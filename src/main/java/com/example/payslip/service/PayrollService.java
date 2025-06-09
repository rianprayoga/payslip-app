package com.example.payslip.service;

import com.example.payslip.controller.payroll.dto.PostPayrollRequest;
import com.example.payslip.controller.payroll.dto.PostPayrollResponse;
import com.example.payslip.data.entity.PayrollEntity;
import com.example.payslip.data.repository.PayrollRepository;
import com.example.payslip.errors.http.BadRequestException;
import com.example.payslip.utilities.DateHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final DateHelper dateHelper;

    public PostPayrollResponse postPayroll(PostPayrollRequest request){

        Long start = dateHelper.toEarlyNight(request.getStartDate());
        Long end = dateHelper.toLateNight(request.getEndDate());

        Optional<PayrollEntity> overlapPayroll = payrollRepository.findBy(start, end);
        if (overlapPayroll.isPresent()) {
            throw new BadRequestException(
                    "Overlap with payroll %s".formatted(overlapPayroll.get().getId()));
        }

        PayrollEntity entity = new PayrollEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(request.getName());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());

        payrollRepository.save(entity);

        return new PostPayrollResponse(entity.getId().toString(), "Payroll added.");
    }

}
