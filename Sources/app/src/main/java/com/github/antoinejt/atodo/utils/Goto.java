package com.github.antoinejt.atodo.utils;

import androidx.appcompat.app.ActionBar;
import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Goto {
    private Goto() {
        // hide the default ctor
    }

    public static void changeActivity(AppCompatActivity origin, Class<? extends Activity> dest) {
        Intent i = new Intent(origin, dest);
        origin.startActivity(i);
    }

    public static void addBackButton(ActionBar actionBar) {
        Objects.requireNonNull(actionBar);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
