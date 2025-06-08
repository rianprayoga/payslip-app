package com.example.payslip.errors.http;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class InternalServerException extends HttpException {

    public InternalServerException( String message) {
        super(INTERNAL_SERVER_ERROR, message);
    }

    public InternalServerException( String message, Throwable cause) {
        super(INTERNAL_SERVER_ERROR, message, cause);
    }
}
