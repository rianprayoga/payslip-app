package com.example.payslip.service;

import com.example.payslip.controller.payroll.dto.PostPayrollRequest;
import com.example.payslip.controller.payroll.dto.PostPayrollResponse;
import com.example.payslip.data.domain.Attendance;
import com.example.payslip.data.domain.Employee;
import com.example.payslip.data.domain.Overtime;
import com.example.payslip.data.domain.Reimbursement;
import com.example.payslip.data.domain.salary.AttendanceSalary;
import com.example.payslip.data.domain.salary.DetailSalary;
import com.example.payslip.data.domain.salary.OvertimeSalary;
import com.example.payslip.data.domain.salary.ReimburseSalary;
import com.example.payslip.data.entity.EmployeeEntity;
import com.example.payslip.data.entity.PayrollEntity;
import com.example.payslip.data.repository.AttendanceRepository;
import com.example.payslip.data.repository.EmployeeRepository;
import com.example.payslip.data.repository.OvertimeRepository;
import com.example.payslip.data.repository.PayrollRepository;
import com.example.payslip.data.repository.ReimburseRepository;
import com.example.payslip.errors.http.BadRequestException;
import com.example.payslip.errors.http.NotFoundException;
import com.example.payslip.utilities.DateHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final AttendanceRepository attendanceRepository;
    private final ReimburseRepository reimburseRepository;
    private final OvertimeRepository overtimeRepository;
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
        entity.setStartDate(start);
        entity.setEndDate(end);
        entity.setCreatedAt(System.currentTimeMillis());
        entity.setUpdatedAt(System.currentTimeMillis());

        payrollRepository.save(entity);

        return new PostPayrollResponse(entity.getId().toString(), "Payroll added.");
    }

    public void payments(String payrollId){

        Optional<PayrollEntity> payroll = payrollRepository.findById(UUID.fromString(payrollId));
        PayrollEntity payrollEntity =
                payroll.orElseThrow(() -> new NotFoundException("Payroll %s not found.".formatted(payroll)));

        if (payrollEntity.isLocked()){
            throw new BadRequestException("Payroll %s already locked.");
        }

        Long startDate = payrollEntity.getStartDate();
        Long endDate = payrollEntity.getEndDate();

        List<Attendance> attendances = attendanceRepository.findAttendanceBy(startDate, endDate);
        List<Reimbursement> reimbursements = reimburseRepository.findBy(startDate, endDate);
        List<Overtime> overtimes = overtimeRepository.findBy(startDate, endDate);

        Map<UUID, AttendanceSalary> attendanceByEmployee = attendances.stream()
                .map(AttendanceSalary::new)
                .collect(Collectors.toMap(AttendanceSalary::getEmployeeId, Function.identity()));

        Map<UUID, List<ReimburseSalary>> reimbursementByEmployee = reimbursements.stream()
                .map(ReimburseSalary::new)
                .collect(Collectors.groupingBy(ReimburseSalary::getEmployeeId));

        Map<UUID, List<OvertimeSalary>> overtimeByEmployee = overtimes.stream()
                .map(OvertimeSalary::new)
                .collect(Collectors.groupingBy(OvertimeSalary::getEmployeeId));

        Set<UUID> employeeIds = new HashSet<>();
        employeeIds.addAll(attendanceByEmployee.keySet());
        employeeIds.addAll(reimbursementByEmployee.keySet());
        employeeIds.addAll(overtimeByEmployee.keySet());

        List<DetailSalary> salaries = employeeIds.stream().map(uuid -> {
            BigDecimal total = BigDecimal.ZERO;

            BigDecimal reimburse = reimbursementByEmployee.getOrDefault(uuid, emptyList())
                    .stream()
                    .map(ReimburseSalary::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            total = total.add(reimburse);

            BigDecimal overtime = overtimeByEmployee.getOrDefault(uuid, emptyList())
                    .stream()
                    .map(OvertimeSalary::getSalary).reduce(BigDecimal.ZERO, BigDecimal::add);
            total = total.add(overtime);

            AttendanceSalary attendance = attendanceByEmployee.getOrDefault(uuid, null);
            if (attendance != null) {
                total = total.add(attendance.getSalary());
            }

            DetailSalary detailSalary = new DetailSalary();
            detailSalary.setEmployeeId(uuid);
            detailSalary.setAttendanceSalary(attendanceByEmployee.get(uuid));
            detailSalary.setReimbursements(reimbursementByEmployee.get(uuid));
            detailSalary.setOvertimeSalaries(overtimeByEmployee.get(uuid));
            detailSalary.setTakeHomePay(total);
            return detailSalary;
        }).toList();

    }

}
