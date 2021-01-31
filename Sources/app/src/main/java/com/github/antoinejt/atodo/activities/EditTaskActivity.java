package com.github.antoinejt.atodo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.models.TaskItem;
import com.github.antoinejt.atodo.utils.DatabaseHandler;
import com.github.antoinejt.atodo.utils.DateFormatter;
import com.github.antoinejt.atodo.utils.ActivityHelper;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

// Sonarlint java:S110 - Inheritance tree of classes should not be too deep
//   I can't do anything about it, that's how Android apps are built.
@SuppressWarnings("java:S110")
public class EditTaskActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    private EditText findEditText(int id) {
        return (EditText) findViewById(id);
    }
    private TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        dateText = findViewById(R.id.fieldTaskEnd);

        findViewById(R.id.fieldTaskEnd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        ActivityHelper.addBackButton(getSupportActionBar());

        final TaskItem item = (TaskItem) getIntent().getSerializableExtra("taskData");
        try (DatabaseHandler db = DatabaseHandler.get(this.getApplicationContext())) {
            final EditText nameField = findEditText(R.id.fieldTaskName);
            final EditText descriptionField = findEditText(R.id.fieldTaskDescription);
            final EditText deadlineField = findEditText(R.id.fieldTaskEnd);
            final CheckBox isFinishedField = findViewById(R.id.fieldIsTaskFinished);
            final SimpleDateFormat formatter = DateFormatter.newFormatter();

            nameField.setText(item.getName());
            descriptionField.setText(item.getDescription());
            deadlineField.setText(formatter.format(item.getDeadline()));
            isFinishedField.setChecked(item.isFinished());

            findViewById(R.id.buttonEdit).setOnClickListener(listener -> {
                Snackbar.make(listener, "To implement", 500).show();
                Date newDeadline = null;
                try {
                    newDeadline = formatter.parse(deadlineField.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                final boolean succeed = db.updateTask(item.getId(), nameField.getText().toString(),
                        descriptionField.getText().toString(),
                        newDeadline, isFinishedField.isChecked());
                final int statusCode = succeed
                        ? R.string.status_update_ok
                        : R.string.status_update_error;
                final String status = getString(statusCode);

                final AppCompatActivity activity = this;
                Snackbar.make(listener, status, 500).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        ActivityHelper.changeActivity(activity, MainActivity.class);
                    }
                }).show();
            });

            findViewById(R.id.buttonDelete).setOnClickListener(listener -> {
                final boolean succeed = db.deleteTask(item.getId());
                final int statusCode = succeed
                        ? R.string.status_delete_ok
                        : R.string.status_delete_error;
                final String status = getString(statusCode);

                final AppCompatActivity activity = this;
                Snackbar.make(listener, status, 500).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        ActivityHelper.changeActivity(activity, MainActivity.class);
                    }
                }).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String fDay, fMonth;
        month++;
        if(dayOfMonth<10)
            fDay = "0" + dayOfMonth;
        else
            fDay=""+dayOfMonth; ;
        if(month<10)
            fMonth = "0" + month;
        else
            fMonth = ""+month;
        String date = fDay +"/"+fMonth+"/"+year;
        dateText.setText(date);
    }

    @Override
    public void onClick(View v) {

    }
}