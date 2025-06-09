package com.example.payslip.utilities;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class DateHelper {

    public Long toMiddleNight(Long date){
        Instant instantToday = Instant.ofEpochMilli(date);
        return instantToday.truncatedTo(ChronoUnit.DAYS).toEpochMilli();
    }

}
