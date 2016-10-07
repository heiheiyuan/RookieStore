package com.snaillemon.rookiestore.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GoodBoy on 10/7/2016.
 */

public class PreferenceUtils {
    public static String PREFERENCE_NAME = "RookieSotore";

    /**
     * put String preference
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putString(Context context,String key,String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        return editor.commit();
    }

    /**
     * get String preference
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context,String key) {
        return getString(context,key,null);
    }
    public static String getString(Context context, String key, String defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE).getString(key,defaultValue);
    }

    /**
     * put int preference
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putInt(Context context,String key,int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        return editor.commit();
    }

    /**
     * get int preferecne
     * @param context
     * @param key
     * @return
     */
    public static int getInt(Context context,String key) {
        return getInt(context,key,-1);
    }
    public static int getInt(Context context, String key, int defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE).getInt(key,defaultValue);
    }

    /**
     * put long preference
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putLong(Context context,String key,long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key,value);
        return editor.commit();
    }

    /**
     * get long preference
     * @param context
     * @param key
     * @return
     */
    public static long getLong(Context context,String key) {
        return getLong(context,key,-1);
    }
    public static long getLong(Context context, String key, long defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE).getLong(key,defaultValue);
    }

    /**
     * put float preference
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putFloat(Context context,String key,float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key,value);
        return editor.commit();
    }

    /**
     * get float preference
     * @param context
     * @param key
     * @return
     */
    public static float getFloat(Context context,String key) {
        return getFloat(context,key,-1);
    }
    public static float getFloat(Context context, String key, float defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE).getFloat(key,defaultValue);
    }

    /**
     * put boolean preference
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putBoolean(Context context,String key,boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        return editor.commit();
    }

    /**
     * get boolean preference
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context,String key) {
        return getBoolean(context,key,false);
    }
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE).getBoolean(key,defaultValue);
    }
}
