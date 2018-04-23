package ru.javawebinar.topjava.util.Formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DateAnnotationFormatterFactory implements AnnotationFormatterFactory<DateFormatter> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = new HashSet<>();
        fieldTypes.add(LocalDate.class);
        return Collections.unmodifiableSet(fieldTypes);
    }

    @Override
    public Printer<?> getPrinter(DateFormatter annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation);
    }

    @Override
    public Parser<?> getParser(DateFormatter annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation);
    }

    private Formatter<LocalDate> configureFormatterFrom(DateFormatter annotation) {
        return new LocalDateFormatter();
    }
}