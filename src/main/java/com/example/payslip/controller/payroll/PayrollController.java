package com.example.payslip.controller.payroll;

import com.electronwill.nightconfig.core.conversion.Path;
import com.example.payslip.config.AuthService;
import com.example.payslip.config.filter.authentication.AdminAuthentication;
import com.example.payslip.controller.payroll.dto.PostPayrollRequest;
import com.example.payslip.controller.payroll.dto.PostPayrollResponse;
import com.example.payslip.service.PayrollService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<String> putPayroll( @PathVariable("payrollId") String payrollId){
        payrollService.payments(payrollId);
        return ResponseEntity.ok("ad");
    }

}
