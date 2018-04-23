package ru.javawebinar.topjava.util.Formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    public String print(LocalTime time, Locale locale) {
        if (time == null) {
            return "";
        }
        return time.toString();
    }

    public LocalTime parse(String formatted, Locale locale) throws ParseException {
        if (formatted.length() == 0) {
            return null;
        }
        return LocalTime.parse(formatted);
    }
}