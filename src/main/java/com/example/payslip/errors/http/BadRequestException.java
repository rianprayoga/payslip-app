package com.example.payslip.errors.http;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends HttpException {

    public BadRequestException(String message) {
        super(BAD_REQUEST,  message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(BAD_REQUEST, message, cause);
    }
}
