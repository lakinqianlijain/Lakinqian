package com.qlj.lakinqiandemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharedPreferenceUtil {
    public static final String VIDEO_CONFIG = "demo_config";

    public static final String PIN_PASSWORD = "pin_password";
    public static final String PIN_EMAIL = "pin_email";

    public static boolean saveString(Context context, String fileName, String key, String value) {
        Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String readString(Context context, String fileName, String key, String defaultvalue) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString(key, defaultvalue);
    }

    public static boolean saveLong(Context context, String fileName, String key, long value) {
        Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static long readLong(Context context, String fileName, String key, long defaultvalue) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultvalue);
    }

    public static boolean saveInt(Context context, String fileName, String key, int value) {
        Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int readInt(Context context, String fileName, String key, int defaultvalue) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultvalue);

    }

    public static boolean saveBoolean(Context context, String fileName, String key, boolean value) {
        Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }


    public static boolean readBoolean(Context context, String fileName, String key, boolean defaultvalue) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultvalue);
    }

    public static boolean contains(Context context, String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public static void remove(Context context, String fileName, String key) {
        Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clear(Context context, String fileName) {
        Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}
