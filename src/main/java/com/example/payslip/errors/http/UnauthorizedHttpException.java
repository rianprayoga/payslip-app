package com.example.payslip.errors.http;

import org.springframework.http.HttpStatus;

public class UnauthorizedHttpException extends HttpException {
    public UnauthorizedHttpException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    UnauthorizedHttpException(String message, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, message, cause);
    }
}
