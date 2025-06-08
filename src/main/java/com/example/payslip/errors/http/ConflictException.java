package com.example.payslip.errors.http;

import static org.springframework.http.HttpStatus.CONFLICT;

public class ConflictException extends HttpException {

    public ConflictException(String message) {
        super(CONFLICT,  message);
    }
}
