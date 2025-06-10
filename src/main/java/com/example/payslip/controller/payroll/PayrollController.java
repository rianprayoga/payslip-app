package com.example.payslip.controller.payroll;

import com.example.payslip.config.AuthService;
import com.example.payslip.config.User;
import com.example.payslip.config.filter.authentication.AdminAuthentication;
import com.example.payslip.config.filter.authentication.EmployeeAuthentication;
import com.example.payslip.controller.payroll.dto.GetPayslipsResponse;
import com.example.payslip.controller.payroll.dto.PostPayrollRequest;
import com.example.payslip.controller.payroll.dto.PostPayrollResponse;
import com.example.payslip.data.domain.salary.DetailSalary;
import com.example.payslip.service.PayrollService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<PostPayrollResponse> postPayroll(@Valid @RequestBody PostPayrollRequest request) {

        authService.getAuthentication(AdminAuthentication.class);

        PostPayrollResponse response = payrollService.postPayroll(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/payrolls/{payrollId}/payments")
    public ResponseEntity<Void> postPayrollPayment(@PathVariable("payrollId") String payrollId) {
        authService.getAuthentication(AdminAuthentication.class);
        payrollService.payments(payrollId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/payrolls/{payrollId}")
    public ResponseEntity<GetPayslipsResponse> adminGetPayroll(@PathVariable("payrollId") String payrollId) {

        authService.getAuthentication(AdminAuthentication.class);

        GetPayslipsResponse response = payrollService.getPayslips(payrollId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/client/payrolls/{payrollId}")
    public ResponseEntity<DetailSalary> getPayroll(@PathVariable("payrollId") String payrollId) {

        EmployeeAuthentication authentication = authService.getAuthentication(EmployeeAuthentication.class);

        DetailSalary response = payrollService.getPayslip(authentication.getUser(), payrollId);
        return ResponseEntity.ok(response);
    }
}
