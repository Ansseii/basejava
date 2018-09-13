package com.mysite.basejava.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;


public class DateUtil {

    private static final LocalDate TEMP_DATE = LocalDate.of(1, 1, 1);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static String format(final LocalDate date) {
        if (date == null) return "";
        return date.equals(TEMP_DATE) ? "По настоящее время" : date.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parse(final String date) {
        if (isEmpty(date) || date.equals("По настоящее время")) return TEMP_DATE;
        YearMonth yearMonth = YearMonth.parse(date, DATE_TIME_FORMATTER);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }

    private static boolean isEmpty(final String str) {
        return str == null || str.trim().length() == 0;
    }
}
