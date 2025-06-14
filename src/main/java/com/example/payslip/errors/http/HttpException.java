package com.example.payslip.errors.http;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {

    private final HttpStatus status;


    HttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    HttpException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;

    }

    public HttpStatus getStatus() {
        return status;
    }


}
