package com.github.antoinejt.atodo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.utils.Goto;
import com.github.antoinejt.atodo.utils.LocaleHelper;

// Sonarlint java:S110 - Inheritance tree of classes should not be too deep
//   I can't do anything about it, that's how Android apps are built.
@SuppressWarnings("java:S110")
public class ChangeLangActivity extends AppCompatActivity {

    final RadioButton English = findViewById(R.id.radio_en),
            French= findViewById(R.id.radio_fr),
            Spanish= findViewById(R.id.radio_es);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        Goto.addBackButton(getSupportActionBar());

        findViewById(R.id.btnChangeLangView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change Application level locale

                //TODO Fix the context problem
                LocaleHelper.setLocale(MainActivity.this, SelectedLang());
                //It is required to recreate the activity to reflect the change in UI.
                recreate();
            }
        });
    }
    String SelectedLang(){
        if(Spanish.isChecked())
            return "es";
        else if(French.isChecked())
            return "fr";
        else
        return "en";
    }
}
