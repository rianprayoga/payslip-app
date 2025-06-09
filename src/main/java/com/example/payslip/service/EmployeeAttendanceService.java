package com.example.payslip.service;

import com.example.payslip.config.User;
import com.example.payslip.controller.attendances.dto.AttendanceType;
import com.example.payslip.controller.attendances.dto.PostAttendanceRequest;
import com.example.payslip.controller.attendances.dto.PostAttendanceResponse;
import com.example.payslip.data.entity.EmployeeAttendanceEntity;
import com.example.payslip.data.repository.AttendanceRepository;
import com.example.payslip.errors.http.BadRequestException;
import com.example.payslip.utilities.DateHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeAttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final DateHelper dateHelper;

    public PostAttendanceResponse postAttendance(User user, PostAttendanceRequest request){

        Optional<EmployeeAttendanceEntity> existingAttendance = getAttendance(user, request);

        if (AttendanceType.CHECK_IN.equals(request.getType())){

            if (existingAttendance.isPresent()){
                throw new BadRequestException("User already check in.");
            }

            validateNotWeekEnd(request.getDate());
            validateDateIsToday(request.getDate());

            EmployeeAttendanceEntity attendance = new EmployeeAttendanceEntity();
            attendance.setId(UUID.randomUUID());
            attendance.setEmployeeId(user.getId());

            long epochAtZero = dateHelper.toEarlyNight(request.getDate());
            attendance.setAttendanceDate(epochAtZero);

            long currentTimeMillis = System.currentTimeMillis();
            attendance.setCheckIn(currentTimeMillis);
            attendance.setCreatedAt(currentTimeMillis);
            attendance.setUpdatedAt(currentTimeMillis);
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
        attendance.setUpdatedAt(currentTime);
        attendanceRepository.save(attendance);

        return PostAttendanceResponse.checkOutResponse("Check out successful", currentTime);
    }

    private Optional<EmployeeAttendanceEntity> getAttendance(User user, PostAttendanceRequest request) {
        long startOfToday = dateHelper.toEarlyNight(request.getDate());
        return attendanceRepository.findByEmployeeIdAndAttendanceDate(user.getId(), startOfToday);
    }

    private void validateNotWeekEnd(Long date){
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(date);
        int i = instance.get(Calendar.DAY_OF_WEEK);

        if (i == Calendar.SUNDAY || i == Calendar.SATURDAY) {
            throw new BadRequestException("User can't add attendance on saturday and sunday.");
        }
    }

    private void validateDateIsToday(Long date){
        long millisInADay = 86_400_000L;

        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        long todayLastNight = instant.truncatedTo(ChronoUnit.DAYS).toEpochMilli();
        long todayNight = todayLastNight + millisInADay - 1000;

        if (todayLastNight <= date && todayNight > date) return;
        throw new BadRequestException(
                "User can't check in neither on the past or future. Check-in must be on the same day as today.");
    }

}
