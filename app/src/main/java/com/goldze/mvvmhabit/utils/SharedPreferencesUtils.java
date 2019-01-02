package com.goldze.mvvmhabit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils INSTANCE;
    private SharedPreferences sharedPreferences;

    private SharedPreferencesUtils() {
    }

    public static SharedPreferencesUtils getInstance() {
        if (INSTANCE == null) INSTANCE = new SharedPreferencesUtils();
        return INSTANCE;
    }

    public String get(Context context, String key, String defaultValue) {
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).apply();
    }
}
