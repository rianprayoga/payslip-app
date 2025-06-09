package com.example.payslip.controller.payroll;

import com.example.payslip.config.AuthService;
import com.example.payslip.config.User;
import com.example.payslip.config.filter.authentication.AdminAuthentication;
import com.example.payslip.config.filter.authentication.EmployeeAuthentication;
import com.example.payslip.controller.payroll.dto.PostPayrollRequest;
import com.example.payslip.controller.payroll.dto.PostPayrollResponse;
import com.example.payslip.controller.reimbusement.dto.PostReimburseRequest;
import com.example.payslip.controller.reimbusement.dto.PostReimburseResponse;
import com.example.payslip.service.EmployeeReimburseService;
import com.example.payslip.service.PayrollService;
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
public class PayrollController {

    private final AuthService authService;
    private final PayrollService payrollService;

    @PostMapping("/admin/payrolls")
    public ResponseEntity<PostPayrollResponse> postReimbursement(@Valid @RequestBody PostPayrollRequest request) {

        authService.getAuthentication(AdminAuthentication.class);

        PostPayrollResponse response = payrollService.postPayroll(request);

        return ResponseEntity.ok(response);
    }

}
