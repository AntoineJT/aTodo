package com.github.antoinejt.atodo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.github.antoinejt.exassert.ExAssert;

public class Preferences {
    private Preferences() {
        // hides the default ctor
    }

    public static void saveBoolean(Context ctx, String key, boolean value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        editor.putBoolean(key, value);
        ExAssert.exAssert(editor.commit());
    }

    public static boolean getBoolean(Context ctx, String key, boolean dfault) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(key, dfault);
    }
}
