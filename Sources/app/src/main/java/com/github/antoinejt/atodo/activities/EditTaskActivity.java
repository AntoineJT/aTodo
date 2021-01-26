package com.github.antoinejt.atodo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.utils.Goto;
import com.google.android.material.snackbar.Snackbar;

// Sonarlint java:S110 - Inheritance tree of classes should not be too deep
//   I can't do anything about it, that's how Android apps are built.
@SuppressWarnings("java:S110")
public class EditTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Goto.addBackButton(getSupportActionBar());

        // TODO Fill the controls with current values

        findViewById(R.id.buttonEdit).setOnClickListener(listener -> {
            // TODO Find a way to transmit the task id here
            Snackbar.make(listener, "To implement", 500).show();
        });

        findViewById(R.id.buttonDelete).setOnClickListener(listener -> {
            // TODO Find a way to transmit the task id here
            Snackbar.make(listener, "To implement", 500).show();
        });
    }
}