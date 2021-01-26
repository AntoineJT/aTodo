package com.github.antoinejt.atodo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.dataclasses.TaskItem;
import com.github.antoinejt.atodo.utils.DBUtils;
import com.github.antoinejt.atodo.utils.DateFormatter;
import com.github.antoinejt.atodo.utils.Goto;
import com.google.android.material.snackbar.Snackbar;

// Sonarlint java:S110 - Inheritance tree of classes should not be too deep
//   I can't do anything about it, that's how Android apps are built.
@SuppressWarnings("java:S110")
public class EditTaskActivity extends AppCompatActivity {
    private EditText findEditText(int id) {
        return (EditText) findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Goto.addBackButton(getSupportActionBar());

        final TaskItem item = (TaskItem) getIntent().getSerializableExtra("taskData");
        try (DBUtils db = DBUtils.get(this.getApplicationContext())) {
            findEditText(R.id.fieldTaskName).setText(item.title);
            findEditText(R.id.fieldTaskDescription).setText(item.description);
            findEditText(R.id.fieldTaskEnd).setText(DateFormatter.formatter.format(item.deadline));
            ((CheckBox) findViewById(R.id.fieldIsTaskFinished)).setChecked(item.isFinished);

            findViewById(R.id.buttonEdit).setOnClickListener(listener -> {
                // TODO Find a way to transmit the task id here
                Snackbar.make(listener, "To implement", 500).show();
            });

            findViewById(R.id.buttonDelete).setOnClickListener(listener -> {
                final boolean succeed = db.deleteTask(item.id);
                final String status = succeed
                        ? "Task successfully deleted!"
                        : "Error while deleting task";

                final AppCompatActivity activity = this;
                Snackbar.make(listener, status, 500).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        Goto.changeActivity(activity, MainActivity.class);
                    }
                }).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}