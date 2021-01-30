package com.github.antoinejt.atodo.activities;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.utils.Goto;
import com.github.antoinejt.atodo.utils.LocaleHelper;

import java.util.HashMap;
import java.util.Map;

// Sonarlint java:S110 - Inheritance tree of classes should not be too deep
//   I can't do anything about it, that's how Android apps are built.
@SuppressWarnings("java:S110")
public class ChangeLangActivity extends AppCompatActivity {
    private static final Map<Integer, String> langMap;

    static {
        langMap = new HashMap<>();
        langMap.put(R.id.radio_en, "en");
        langMap.put(R.id.radio_es, "es");
        langMap.put(R.id.radio_fr, "fr");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        Goto.addBackButton(getSupportActionBar());

        final AppCompatActivity activity = this;
        findViewById(R.id.btnChangeLangView).setOnClickListener(view -> {
            RadioGroup rg = findViewById(R.id.selected_lang);
            LocaleHelper.setLocale(activity, langMap.get(rg.getCheckedRadioButtonId()));
            // It is required to recreate the activity to reflect the change in UI.
            recreate();
        });
    }
}
