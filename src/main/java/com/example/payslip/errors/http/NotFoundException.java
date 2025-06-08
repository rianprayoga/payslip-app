package com.example.payslip.errors.http;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends HttpException {

    public NotFoundException(String message) {
        super(NOT_FOUND,  message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(NOT_FOUND, message, cause);
    }
}
