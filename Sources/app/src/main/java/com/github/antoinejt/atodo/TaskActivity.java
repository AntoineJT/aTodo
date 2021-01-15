package com.github.antoinejt.atodo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        Button bt1 = findViewById(R.id.buttonOkay);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Db add + return
                task();
            }
        });
    }

    private void task(){
        //TODO Verify getApplicationContext / getBaseContext
        DBUtils db = new DBUtils(this.getApplicationContext());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        EditText editTaskName = (EditText) findViewById(R.id.fieldTaskName);
        String taskName = editTaskName.getText().toString();

        EditText editTaskEnd = (EditText) findViewById(R.id.fieldTaskEnd);
        String taskEnd = editTaskEnd.getText().toString();

        EditText editTaskDescription = (EditText) findViewById(R.id.fieldTaskDescription);
        String taskDescription = editTaskDescription.getText().toString();

        db.createTaskList(taskName, currentDate, taskEnd);
        db.insertTask(taskName,taskDescription, taskEnd);
    }

    @Override
    public void onClick(View v) {
    }
}