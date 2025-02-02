package com.github.antoinejt.atodo.activities;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.github.antoinejt.atodo.R;
import com.github.antoinejt.atodo.utils.BiMap;
import com.github.antoinejt.atodo.utils.ActivityHelper;

import java.util.Locale;

// Sonarlint java:S110 - Inheritance tree of classes should not be too deep
//   I can't do anything about it, that's how Android apps are built.
@SuppressWarnings("java:S110")
public class ChangeLangActivity extends LocalizationActivity {
    private static final BiMap<Integer, String> langCodes;

    static {
        langCodes = new BiMap<>();
        langCodes.put(R.id.radio_en, "en");
        langCodes.put(R.id.radio_es, "es");
        langCodes.put(R.id.radio_fr, "fr");
    }

    private void checkDefaultLanguage(RadioGroup rg) {
        String defaultLangCode = Locale.getDefault().getLanguage();
        if (!langCodes.get().containsValue(defaultLangCode)) {
            // fallback language
            defaultLangCode = "en";
        }
        rg.check(langCodes.inverted().get(defaultLangCode));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        ActivityHelper.addBackButton(getSupportActionBar());

        final RadioGroup rg = findViewById(R.id.selected_lang);

        checkDefaultLanguage(rg);

        findViewById(R.id.btnChangeLangView).setOnClickListener(view -> {
            final String langCode = langCodes.get().get(rg.getCheckedRadioButtonId());
            setLanguage(langCode);
        });
    }
}
