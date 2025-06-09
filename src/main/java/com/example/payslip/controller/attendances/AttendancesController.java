package com.example.payslip.controller.attendances;

import com.example.payslip.config.AuthService;
import com.example.payslip.config.User;
import com.example.payslip.config.filter.authentication.EmployeeAuthentication;
import com.example.payslip.controller.attendances.dto.PostAttendanceRequest;
import com.example.payslip.controller.attendances.dto.PostAttendanceResponse;
import com.example.payslip.service.EmployeeAttendanceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.payslip.controller.UrlConstant.V1;

@Validated
@RestController
@RequestMapping(V1)
@AllArgsConstructor
public class AttendancesController {

    private final AuthService authService;
    private final EmployeeAttendanceService employeeAttendanceService;

    @PostMapping("/client/attendances")
    public ResponseEntity<PostAttendanceResponse> postAttendance(@Valid @RequestBody PostAttendanceRequest request){

        EmployeeAuthentication authentication = authService.getAuthentication(EmployeeAuthentication.class);
        User user = authentication.getUser();

        PostAttendanceResponse response = employeeAttendanceService.postAttendance(user, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/what")
    public ResponseEntity<String> testAdmin(){
        return ResponseEntity.ok("WHAT");
    }



}
