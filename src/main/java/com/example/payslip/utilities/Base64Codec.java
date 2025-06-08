package com.example.payslip.utilities;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64Codec {

    public String decode(String value){
        byte[] decodedBytes = Base64.getDecoder().decode(value.getBytes());
        return new String(decodedBytes);
    }
}
