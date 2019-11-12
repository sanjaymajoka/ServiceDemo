package com.servicedemo.app;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static String SUCCESS = "_success";
    public static String FAILED = "failed";

    public static void putString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }



}
