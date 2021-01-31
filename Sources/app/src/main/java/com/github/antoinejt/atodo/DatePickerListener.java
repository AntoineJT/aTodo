package com.github.antoinejt.atodo;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerListener implements DatePickerDialog.OnDateSetListener {
    private final TextView dateText;

    public DatePickerListener(TextView dateText) {
        this.dateText = dateText;
    }

    private String addLeadingZero(int i) {
        return i < 10 ? "0" + i : String.valueOf(i);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final String formattedDay = addLeadingZero(dayOfMonth);
        final String formattedMonth = addLeadingZero(month + 1);
        final String date = formattedDay + "/" + formattedMonth + "/" + year;
        dateText.setText(date);
    }
}
