package com.github.antoinejt.atodo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.dataclasses.TaskItem;
import com.github.antoinejt.atodo.utils.DBUtils;
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

        final TaskItem item = (TaskItem) getIntent().getSerializableExtra("taskData");
        try (DBUtils db = new DBUtils(this.getApplicationContext())) {
            // TODO Fill the controls with current values

            findViewById(R.id.buttonEdit).setOnClickListener(listener -> {
                // TODO Find a way to transmit the task id here
                Snackbar.make(listener, "To implement", 500).show();
            });

            findViewById(R.id.buttonDelete).setOnClickListener(listener -> {
                final boolean succeed = db.deleteTask(item.id);
                final String status = succeed
                        ? "Task successfully deleted!"
                        : "Error while deleting task";

                Snackbar.make(listener, status, 500).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}