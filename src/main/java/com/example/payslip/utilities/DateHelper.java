package com.example.payslip.utilities;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class DateHelper {

    public Long toEarlyNight(Long date){
        Instant instantToday = Instant.ofEpochMilli(date);
        return instantToday.truncatedTo(ChronoUnit.DAYS).toEpochMilli();
    }

    public Long toLateNight(Long date){
        int oneSecond = 1000;
        int dayInMillis = 86_400_000;
        Instant instantToday = Instant.ofEpochMilli(date);
        return instantToday.truncatedTo(ChronoUnit.DAYS).plusMillis(dayInMillis).minusMillis(oneSecond).toEpochMilli();
    }

}
