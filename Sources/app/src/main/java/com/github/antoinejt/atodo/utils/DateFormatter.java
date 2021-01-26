package com.github.antoinejt.atodo.utils;

import android.icu.text.SimpleDateFormat;

import java.util.Locale;

public class DateFormatter {
    private DateFormatter() {
        // hides default ctor
    }

    public static final SimpleDateFormat formatter =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
}
