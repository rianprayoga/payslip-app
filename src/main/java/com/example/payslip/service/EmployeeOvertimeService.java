package com.example.payslip.service;

import com.example.payslip.config.User;
import com.example.payslip.controller.overtimes.dto.PostOvertimeRequest;
import com.example.payslip.controller.overtimes.dto.PostOvertimeResponse;
import com.example.payslip.data.entity.EmployeeAttendanceEntity;
import com.example.payslip.data.entity.EmployeeOvertimeEntity;
import com.example.payslip.data.repository.AttendanceRepository;
import com.example.payslip.data.repository.OvertimeRepository;
import com.example.payslip.data.repository.PayrollRepository;
import com.example.payslip.errors.http.BadRequestException;
import com.example.payslip.utilities.DateHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeOvertimeService {

    public static final int OVERTIME_LIMIT_IN_A_DAY = 3;
    private final AttendanceRepository attendanceRepository;
    private final OvertimeRepository overtimeRepository;
    private final PayrollRepository payrollRepository;
    private final DateHelper dateHelper;

    public PostOvertimeResponse postOvertime(User user, PostOvertimeRequest request) {

        Optional<UUID> payroll = payrollRepository.findCurrentPayrollBy(request.getOvertimeDate());
        if (payroll.isPresent()){
            throw new BadRequestException("Current payroll already locked.");
        }

        Long submissionDate = dateHelper.toEarlyNight(System.currentTimeMillis());

        Optional<EmployeeAttendanceEntity> attendance =
                attendanceRepository.findByEmployeeIdAndAttendanceDate(user.getId(), submissionDate);

        EmployeeAttendanceEntity attendanceEntity = attendance.orElseThrow(
                () -> new BadRequestException("Can't propose overtime, you haven't finished today's time."));

        if (attendanceEntity.getCheckOut() == null) {
            throw new BadRequestException("Can't propose overtime, you haven't check-out today.");
        }

        Long overtimeDate = dateHelper.toEarlyNight(request.getOvertimeDate());
        List<EmployeeOvertimeEntity> overtime =
                overtimeRepository.findByEmployeeIdAndOvertimeDate(user.getId(), overtimeDate);

        Integer durationDesignatedDate =
                overtime.stream().map(EmployeeOvertimeEntity::getDuration).reduce(0, Integer::sum);
        if (durationDesignatedDate == OVERTIME_LIMIT_IN_A_DAY){
            throw new BadRequestException("Can't propose overtime, overtime duration already reached limit.");
        }

        EmployeeOvertimeEntity overtimeEntity = new EmployeeOvertimeEntity();
        overtimeEntity.setId(UUID.randomUUID());
        overtimeEntity.setEmployeeId(user.getId());
        overtimeEntity.setSubmissionDate(submissionDate);
        overtimeEntity.setDuration(request.getDuration());
        overtimeEntity.setOvertimeDate(overtimeDate);

        long current = System.currentTimeMillis();
        overtimeEntity.setCreatedAt(current);
        overtimeEntity.setUpdatedAt(current);

        overtimeRepository.save(overtimeEntity);

        return new PostOvertimeResponse(overtimeEntity.getId().toString(), "Overtime added.");
    }
}
