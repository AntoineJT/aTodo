package com.github.antoinejt.atodo.activities;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.utils.Goto;

import java.util.HashMap;
import java.util.Map;

// Sonarlint java:S110 - Inheritance tree of classes should not be too deep
//   I can't do anything about it, that's how Android apps are built.
@SuppressWarnings("java:S110")
public class ChangeLangActivity extends LocalizationActivity {
    private static final Map<Integer, String> langCodes;

    static {
        langCodes = new HashMap<>();
        langCodes.put(R.id.radio_en, "en");
        langCodes.put(R.id.radio_es, "es");
        langCodes.put(R.id.radio_fr, "fr");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        Goto.addBackButton(getSupportActionBar());

        final AppCompatActivity activity = this;
        findViewById(R.id.btnChangeLangView).setOnClickListener(view -> {
            RadioGroup rg = findViewById(R.id.selected_lang);
            String langCode = langCodes.get(rg.getCheckedRadioButtonId());
            setLanguage(langCode);
        });
    }
}
