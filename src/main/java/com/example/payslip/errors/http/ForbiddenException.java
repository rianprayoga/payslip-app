package com.example.payslip.errors.http;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ForbiddenException extends HttpException {
    public ForbiddenException(String message) {
        super(FORBIDDEN, message);
    }

    public ForbiddenException( String message, Throwable cause) {
        super(FORBIDDEN, message, cause);
    }
}
