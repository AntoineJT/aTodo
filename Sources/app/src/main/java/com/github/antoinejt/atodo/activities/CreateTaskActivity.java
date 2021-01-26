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

import java.util.Date;
import java.util.Locale;

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
            String status = succeed ? "Task saved correctly" : "Error while saving task";
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

    private EditText findEditText(int id) {
        return (EditText) findViewById(id);
    }

    private boolean createTask() {
        //TODO Verify getApplicationContext / getBaseContext
        try (DBUtils db = new DBUtils(this.getApplicationContext())) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String currentDate = sdf.format(new Date());

            String taskName = findEditText(R.id.fieldTaskName).getText().toString();
            String taskEnd = findEditText(R.id.fieldTaskEnd).getText().toString();
            String taskDescription = findEditText(R.id.fieldTaskDescription).getText().toString();

            long taskListId = db.createTaskList(taskName, currentDate, taskEnd);

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
