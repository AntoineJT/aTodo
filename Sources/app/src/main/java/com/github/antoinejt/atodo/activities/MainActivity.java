package com.github.antoinejt.atodo.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import androidx.appcompat.widget.Toolbar;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.TaskListFragment;
import com.github.antoinejt.atodo.utils.Goto;
import com.github.antoinejt.atodo.utils.Preferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

// Sonarlint java:S110 - Inheritance tree of classes should not be too deep
//   I can't do anything about it, that's how Android apps are built.
// Sonarlint java:S2696 - Instance methods should not write to "static" fields
//   I want to store a reference to the latest instance, more or less
//   like in a singleton pattern, so I must write to a static field.
@SuppressWarnings({"java:S110", "java:S2696"})
public class MainActivity extends LocalizationActivity {
    private static MainActivity instance;
    private static boolean firstRun = false;

    private static final String HIDE_TASKS_PREFERENCES = "PrefersToHideTasks";

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        boolean hideUnfinishedTasks = Preferences.getBoolean(getApplicationContext(),
                HIDE_TASKS_PREFERENCES, false);

        // set last selected language only at startup
        if (!firstRun) {
            setLanguage(Locale.getDefault());
            TaskListFragment.setHideUnfinishedTasks(hideUnfinishedTasks);
            firstRun = true;
        }

        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Goto.changeActivity(this, CreateTaskActivity.class));

        final Switch hideUnfinished = findViewById(R.id.hide_switch);
        hideUnfinishedTasks |= TaskListFragment.isHidingUnfinishedTasks();
        hideUnfinished.setChecked(hideUnfinishedTasks);
        hideUnfinished.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (TaskListFragment.isHidingUnfinishedTasks() != isChecked) {
                TaskListFragment.setHideUnfinishedTasks(isChecked);
                Preferences.saveBoolean(getApplicationContext(), HIDE_TASKS_PREFERENCES, isChecked);
                Goto.refresh(instance);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        if (id == R.id.action_settings) {
            Goto.changeActivity(this, ChangeLangActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }
}