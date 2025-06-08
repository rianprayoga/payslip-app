package com.example.payslip.errors.http;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

public class ServiceUnavailableException extends HttpException {

    public ServiceUnavailableException( String message) {
        super(SERVICE_UNAVAILABLE, message);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(SERVICE_UNAVAILABLE, message, cause);
    }
}
