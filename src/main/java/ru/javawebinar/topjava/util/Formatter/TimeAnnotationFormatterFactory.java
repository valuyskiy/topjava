package ru.javawebinar.topjava.util.Formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TimeAnnotationFormatterFactory implements AnnotationFormatterFactory<TimeFormatter> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = new HashSet<>();
        fieldTypes.add(LocalTime.class);
        return Collections.unmodifiableSet(fieldTypes);
    }

    @Override
    public Printer<?> getPrinter(TimeFormatter annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation);
    }

    @Override
    public Parser<?> getParser(TimeFormatter annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation);
    }

    private Formatter<LocalTime> configureFormatterFrom(TimeFormatter annotation) {
        return new LocalTimeFormatter();
    }
}