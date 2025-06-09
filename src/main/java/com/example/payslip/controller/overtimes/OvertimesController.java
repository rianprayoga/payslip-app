package com.example.payslip.controller.overtimes;

import com.example.payslip.config.AuthService;
import com.example.payslip.config.User;
import com.example.payslip.config.filter.authentication.EmployeeAuthentication;
import com.example.payslip.controller.overtimes.dto.PostOvertimeRequest;
import com.example.payslip.controller.overtimes.dto.PostOvertimeResponse;
import com.example.payslip.service.EmployeeOvertimeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.payslip.controller.UrlConstant.V1;

@Validated
@RestController
@RequestMapping(V1)
@AllArgsConstructor
public class OvertimesController {

    private final AuthService authService;
    private final EmployeeOvertimeService overtimeService;

    @PostMapping("/client/overtimes")
    public ResponseEntity<PostOvertimeResponse> postOvertime(@Valid @RequestBody PostOvertimeRequest request) {

        EmployeeAuthentication authentication = authService.getAuthentication(EmployeeAuthentication.class);
        User user = authentication.getUser();

        PostOvertimeResponse response = overtimeService.postOvertime(user, request);

        return ResponseEntity.ok(response);
    }
}
