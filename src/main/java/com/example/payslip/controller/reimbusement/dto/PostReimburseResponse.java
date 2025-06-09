package com.example.payslip.controller.reimbusement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostReimburseResponse {

    private String reimbursementId;
    private String message;
}
