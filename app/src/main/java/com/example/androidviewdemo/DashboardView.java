package com.example.androidviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 自定义仪表盘View
 */
public class DashboardView extends View {
    public DashboardView(Context context) {
        super(context);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
