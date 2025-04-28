package com.sd.pms.app.util;

import com.sd.pms.app.entity.Recommendation;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
public class FindJobFire {

    public static LocalDate nextDay(LocalDate currentDate) {
        return currentDate.plusDays(1);
    }

    public static LocalDate nextSunday(LocalDate currentDate) {
        return currentDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
    }

    public static LocalDate thisMonthsEnd(LocalDate currentDate) {
        return currentDate.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDate thisYearsEnd(LocalDate currentDate) {
        return currentDate.with(TemporalAdjusters.lastDayOfYear());
    }

    public static LocalDate createNextFireDate(Recommendation recommendation) {

        LocalDate nextFireDay = LocalDate.now();

        switch (recommendation.getOccurrence()) {
            case DAILY:
                nextFireDay = FindJobFire.nextDay(LocalDate.now());
                break;

            case WEEKLY:
                nextFireDay = FindJobFire.nextSunday(LocalDate.now());
                break;

            case MONTHLY:
                nextFireDay = FindJobFire.thisMonthsEnd(LocalDate.now());
                break;

            case YEARLY:
                nextFireDay = FindJobFire.thisYearsEnd(LocalDate.now());
                break;

            default:
                break;
        }

        return nextFireDay;
    }

}
