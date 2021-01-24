package com.github.antoinejt.atodo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.antoinejt.atodo.utils.Goto;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        final AppCompatActivity activity = this;
        Button okayButton = findViewById(R.id.buttonOkay);
        okayButton.setOnClickListener(v -> {
            boolean succeed = createTask();
            Snackbar.make(v, succeed ? "Task saved correctly" : "Error while saving task", 750)
                    .addCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            super.onDismissed(snackbar, event);
                            Goto.changeActivity(activity, MainActivity.class);
                        }}).show();
        });
    }

    private boolean createTask(){
        //TODO Verify getApplicationContext / getBaseContext
        boolean result;

        try (DBUtils db = new DBUtils(this.getApplicationContext())) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String currentDate = sdf.format(new Date());

            EditText editTaskName = (EditText) findViewById(R.id.fieldTaskName);
            String taskName = editTaskName.getText().toString();

            EditText editTaskEnd = (EditText) findViewById(R.id.fieldTaskEnd);
            String taskEnd = editTaskEnd.getText().toString();

            EditText editTaskDescription = (EditText) findViewById(R.id.fieldTaskDescription);
            String taskDescription = editTaskDescription.getText().toString();

            result = db.createTaskList(taskName, currentDate, taskEnd) && db.insertTask(taskName,taskDescription, taskEnd);
        }

        return result;
    }

    @Override
    public void onClick(View v) {
    }
}