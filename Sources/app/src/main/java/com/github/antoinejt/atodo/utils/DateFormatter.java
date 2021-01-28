package com.github.antoinejt.atodo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    private DateFormatter() {
        // hides default ctor
    }

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    public static SimpleDateFormat newFormatter() {
        return new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
    }

    public static String format(Date date) {
        return newFormatter().format(date);
    }

    public static Date parse(String string) throws ParseException {
        return newFormatter().parse(string);
    }
}
