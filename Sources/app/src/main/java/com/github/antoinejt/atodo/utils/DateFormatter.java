package com.github.antoinejt.atodo.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateFormatter {
    private DateFormatter() {
        // hides default ctor
    }

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    public static final SimpleDateFormat newFormatter() {
        return new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
    }
}
