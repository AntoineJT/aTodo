package com.github.antoinejt.atodo.activities;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.utils.DBUtils;
import com.github.antoinejt.atodo.utils.Goto;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

// Sonarlint java:S110 - Inheritance tree of classes should not be too deep
//   I can't do anything about it, that's how Android apps are built.
@SuppressWarnings("java:S110")
public class CreateTaskActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Goto.addBackButton(getSupportActionBar());

        final AppCompatActivity activity = this;
        findViewById(R.id.buttonCreate).setOnClickListener(listener -> {
            final TaskCreationStatus status = createTask();
            final Snackbar snack = Snackbar.make(listener, status.getStatus(), 350);
            if (status == TaskCreationStatus.OK) {
                snack.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        Goto.changeActivity(activity, MainActivity.class);
                    }
                });
            }
            snack.show();
        });
    }

    private String getEditTextValue(int id) {
        return ((EditText) findViewById(id)).getText().toString();
    }

    enum TaskCreationStatus {
        OK("Task saved correctly!"),
        EMPTY_FIELDS("Error: Some fields are empty!"),
        DATE_FORMAT("Error: Date format is incorrect!"),
        ERROR("Error while saving the database!");

        private final String status;

        TaskCreationStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    private TaskCreationStatus createTask() {
        //TODO Verify getApplicationContext / getBaseContext
        try (DBUtils db = new DBUtils(this.getApplicationContext())) {
            final List<Integer> fieldsId = Arrays.asList(
                    R.id.fieldTaskName,
                    R.id.fieldTaskEnd,
                    R.id.fieldTaskDescription);
            // order: name, deadline, description
            final List<String> values = fieldsId.stream()
                    .map(this::getEditTextValue)
                    .collect(Collectors.toList());

            if (values.stream().anyMatch(String::isEmpty)) {
                return TaskCreationStatus.EMPTY_FIELDS;
            }

            final String name = values.get(0);
            final String deadline = values.get(1);
            final String description = values.get(2);

            if (!isDeadlineCorrect(deadline)) {
                return TaskCreationStatus.DATE_FORMAT;
            }

            final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            final boolean succeed = db.createTask(name, description, sdf.parse(deadline)) != -1;

            return succeed ? TaskCreationStatus.OK : TaskCreationStatus.ERROR;
        } catch (Exception e) {
            return TaskCreationStatus.ERROR;
        }
    }

    private boolean isDeadlineCorrect(String deadline) {
        String[] split = deadline.split("/");
        if (split.length != 3) {
            return false;
        }

        return split[0].length() == 2
                && split[1].length() == 2
                && split[2].length() == 4;
    }

    @Override
    public void onClick(View v) {
        // we don't care of this event
    }
}
