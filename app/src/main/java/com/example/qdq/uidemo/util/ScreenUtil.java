package com.example.qdq.uidemo.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created on 2017/5/18.
 * Author: qdq
 * Description:获取屏幕宽高
 */

public class ScreenUtil {
    public static int getScreenWidth(Context context){
        DisplayMetrics metrics=context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }
    public static int getScreenHeight(Context context){
        DisplayMetrics metrics=context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
}
