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
import java.util.Date;
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
            final boolean succeed = createTask();
            final String status = succeed
                    ? "Task saved correctly"
                    : "Error while saving task (are all fields filled?)";
            Snackbar snack = Snackbar.make(listener, status, 350);
            if (succeed) {
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

    private boolean createTask() {
        //TODO Verify getApplicationContext / getBaseContext
        try (DBUtils db = new DBUtils(this.getApplicationContext())) {
            final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            final String currentDate = sdf.format(new Date());

            // TODO S'occuper de la deadline

            final List<Integer> fieldsId =
                    Arrays.asList(R.id.fieldTaskName, R.id.fieldTaskEnd, R.id.fieldTaskDescription);
            // order: name, end, description
            final List<String> values = fieldsId.stream()
                    .map(this::getEditTextValue)
                    .collect(Collectors.toList());

            if (values.stream().anyMatch(String::isEmpty)) {
                return false;
            }

            final String taskName = values.get(0);
            final String taskEnd = values.get(1);
            final String taskDescription = values.get(2);

            final long taskListId = db.createTaskList(taskName, currentDate, taskEnd);

            return taskListId != -1
                    && db.createTask(taskListId, taskName, taskDescription) != -1;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        // we don't care of this event
    }
}
