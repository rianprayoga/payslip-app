package com.example.payslip.controller.reimbusement;

import com.example.payslip.config.AuthService;
import com.example.payslip.config.User;
import com.example.payslip.config.filter.authentication.EmployeeAuthentication;
import com.example.payslip.controller.reimbusement.dto.PostReimburseRequest;
import com.example.payslip.controller.reimbusement.dto.PostReimburseResponse;
import com.example.payslip.service.EmployeeReimburseService;
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
public class ReimbursementController {

    private final AuthService authService;
    private final EmployeeReimburseService reimburseService;

    @PostMapping("/client/reimbursements")
    public ResponseEntity<PostReimburseResponse> postReimbursement(@Valid @RequestBody PostReimburseRequest request) {

        EmployeeAuthentication authentication = authService.getAuthentication(EmployeeAuthentication.class);
        User user = authentication.getUser();

        PostReimburseResponse response = reimburseService.postReimburse(user, request);
        return ResponseEntity.ok(response);
    }
}
