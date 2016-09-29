package com.snaillemon.rookiestore.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by GoodBoy on 9/29/2016.
 */

public class ToastUtils {
    public static void show(Context context,int resId) {
        show(context,context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }
    public static void show(Context context,int resId,Object... args) {
        show(context,String.format(context.getResources().getString(resId),args),Toast.LENGTH_SHORT);
    }
    public static void show(Context context,int resId,int duration) {
        show(context,context.getResources().getText(resId), duration);
    }
    public static void show(Context context,int resId,int duration,Object... args) {
        show(context,String.format(context.getResources().getString(resId),args),duration);
    }
    public static void show(Context context,CharSequence text) {
        show(context,text,Toast.LENGTH_SHORT);
    }
    public static void show(Context context,CharSequence text,int duration) {
        Toast.makeText(context,text,duration).show();
    }
    public static void show(Context context,String str,Object... args) {
        show(context,String.format(str,args),Toast.LENGTH_SHORT);
    }
    public static void show(Context context,String str,int duration,Object... args) {
        show(context,String.format(str,args),duration);
    }
}
