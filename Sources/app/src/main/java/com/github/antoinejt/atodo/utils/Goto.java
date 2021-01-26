package com.github.antoinejt.atodo.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.antoinejt.atodo.models.TaskItem;

import java.util.Objects;

public class Goto {
    private Goto() {
        // hide the default ctor
    }

    public static void changeActivity(AppCompatActivity origin, Class<? extends Activity> dest) {
        Intent i = new Intent(origin, dest);
        origin.startActivity(i);
    }

    public static void changeActivity(AppCompatActivity origin, Class<? extends Activity> dest, TaskItem task) {
        Intent i = new Intent(origin, dest);
        i.putExtra("taskData", task);
        origin.startActivity(i);
    }

    public static void addBackButton(ActionBar actionBar) {
        Objects.requireNonNull(actionBar);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
