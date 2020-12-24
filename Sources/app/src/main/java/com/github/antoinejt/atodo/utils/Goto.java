package com.github.antoinejt.atodo.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class Goto {
    public static void changeActivity(AppCompatActivity origin, Class<? extends Activity> dest) {
        Intent i = new Intent(origin, dest);
        origin.startActivity(i);
    }
}
