package com.example.hencoderl14_viewpager.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ScreenUtil {
    private static final DisplayMetrics DISPLAY_METRICS;

    static {
        DISPLAY_METRICS = Resources.getSystem().getDisplayMetrics();
    }
    public static float dp2Px(int dp){
        return DISPLAY_METRICS.density * dp;
    }
}
