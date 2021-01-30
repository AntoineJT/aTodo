package com.github.antoinejt.atodo.activities;

import android.os.Bundle;
import android.content.Context;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.utils.Goto;
import java.util.Locale;

public class ChangeLangActivity extends AppCompatActivity {
    private EditText findEditText(int id) {
        return (EditText) findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        Goto.addBackButton(getSupportActionBar());
    final RadioButton   English = findViewById(R.id.radio_en),
                        French= findViewById(R.id.radio_fr),
                        Spanish= findViewById(R.id.radio_es);




    }
}
