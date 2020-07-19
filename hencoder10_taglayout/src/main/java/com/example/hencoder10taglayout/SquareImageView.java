package com.example.hencoder10taglayout;


import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 例子1：方形ImageView
 * 修改尺寸的方式：
 * 1、重写layout，在super.layout里设置修正值---不建议，父布局无法用新的尺寸进行布局，导致存在偏差
 *
 * 2、重写onMeasure，调用setMeasuredDimension设置新的值
 */
public class SquareImageView extends AppCompatImageView {
    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 1、调用原始测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 2、计算修正值
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = Math.min(width, height);
        // 3、设置新的修正值
        setMeasuredDimension(size, size);
    }
}
