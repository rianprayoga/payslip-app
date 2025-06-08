package com.example.payslip.errors;


import com.example.payslip.errors.http.HttpException;

public record ErrorResponse(String message) {

    public static ErrorResponse from(HttpException e) {
        return new ErrorResponse(e.getMessage());
    }

    public static ErrorResponse from(String message) {
        return new ErrorResponse(message);
    }

}
