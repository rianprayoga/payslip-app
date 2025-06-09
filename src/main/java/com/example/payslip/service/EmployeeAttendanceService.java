package com.example.payslip.service;

import com.example.payslip.config.User;
import com.example.payslip.controller.attendances.dto.AttendanceType;
import com.example.payslip.controller.attendances.dto.PostAttendanceRequest;
import com.example.payslip.controller.attendances.dto.PostAttendanceResponse;
import com.example.payslip.data.entity.EmployeeAttendanceEntity;
import com.example.payslip.data.repository.AttendanceRepository;
import com.example.payslip.errors.http.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeAttendanceService {

    private final AttendanceRepository attendanceRepository;

    public PostAttendanceResponse postAttendance(User user, PostAttendanceRequest request){

        Optional<EmployeeAttendanceEntity> existingAttendance = getAttendance(user, request);

        if (AttendanceType.CHECK_IN.equals(request.getType())){

            if (existingAttendance.isPresent()){
                throw new BadRequestException("User already check in.");
            }

            EmployeeAttendanceEntity attendance = new EmployeeAttendanceEntity();
            attendance.setId(UUID.randomUUID());
            attendance.setEmployeeId(user.getId());

            Instant instantToday = Instant.ofEpochMilli(request.getDate());
            long epochAtZero = instantToday.truncatedTo(ChronoUnit.DAYS).toEpochMilli();
            attendance.setAttendanceDate(epochAtZero);

            long currentTimeMillis = System.currentTimeMillis();
            attendance.setCheckIn(currentTimeMillis);
            attendance.setCreatedOn(currentTimeMillis);
            attendance.setModifiedOn(currentTimeMillis);
            attendanceRepository.save(attendance);

            return PostAttendanceResponse.checkInResponse("Check in successful", currentTimeMillis);
        }

        EmployeeAttendanceEntity attendance = existingAttendance.orElseThrow(
                () -> new BadRequestException("User has no check in record at the given date."));

        if (attendance.getCheckOut() != null) {
            throw new BadRequestException("User already checkout.");
        }

        long currentTime = System.currentTimeMillis();
        attendance.setCheckOut(currentTime);
        attendance.setModifiedOn(currentTime);
        attendanceRepository.save(attendance);

        return PostAttendanceResponse.checkOutResponse("Check out successful", currentTime);
    }

    private Optional<EmployeeAttendanceEntity> getAttendance(User user, PostAttendanceRequest request) {
        Instant instantToday = Instant.ofEpochMilli(request.getDate());
        long startOfToday = instantToday.truncatedTo(ChronoUnit.DAYS).toEpochMilli();

        return attendanceRepository.findByEmployeeIdAndAttendanceDate(user.getId(), startOfToday);
    }

}
