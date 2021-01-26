package com.github.antoinejt.atodo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.models.TaskItem;
import com.github.antoinejt.atodo.utils.DatabaseHandler;
import com.github.antoinejt.atodo.utils.DateFormatter;
import com.github.antoinejt.atodo.utils.Goto;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.Date;

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
        try (DatabaseHandler db = DatabaseHandler.get(this.getApplicationContext())) {
            final EditText nameField = findEditText(R.id.fieldTaskName);
            final EditText descriptionField = findEditText(R.id.fieldTaskDescription);
            final EditText deadlineField = findEditText(R.id.fieldTaskEnd);
            final CheckBox isFinishedField = findViewById(R.id.fieldIsTaskFinished);

            nameField.setText(item.title);
            descriptionField.setText(item.description);
            deadlineField.setText(DateFormatter.formatter.format(item.deadline));
            isFinishedField.setChecked(item.isFinished);

            findViewById(R.id.buttonEdit).setOnClickListener(listener -> {
                Snackbar.make(listener, "To implement", 500).show();
                Date newDeadline = null;
                try {
                    newDeadline = DateFormatter.formatter.parse(deadlineField.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                final boolean succeed = db.updateTask(item.id, nameField.getText().toString(),
                        descriptionField.getText().toString(),
                        newDeadline, isFinishedField.isChecked());
                final String status = succeed
                        ? "Task successfully updated!"
                        : "Error while updating task!";

                final AppCompatActivity activity = this;
                Snackbar.make(listener, status, 500).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        Goto.changeActivity(activity, MainActivity.class);
                    }
                }).show();
            });

            findViewById(R.id.buttonDelete).setOnClickListener(listener -> {
                final boolean succeed = db.deleteTask(item.id);
                final String status = succeed
                        ? "Task successfully deleted!"
                        : "Error while deleting task!";

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