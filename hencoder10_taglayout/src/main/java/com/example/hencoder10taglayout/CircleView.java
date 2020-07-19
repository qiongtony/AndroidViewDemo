package com.example.hencoder10taglayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.hencoder10taglayout.util.ScreenUtil;

/**
 * 画带padding的圆，该圆会根据自己的尺寸和父View期望尺寸得出最终尺寸
 * 自己测量尺寸，并根据父View的期望尺寸，设置最终的尺寸
 * 主要方法：
 * resolveSize(size, measureSpec)---该方法帮我们做了相应的处理，其实就是根据自己的尺寸和期望尺寸进行的处理是固定的，所以弄了模板方法给我们用
 */
public class CircleView extends View {
    private static final int RADIUS_SIZE = (int) ScreenUtil.dp2Px(100);
    private static final int PADDING = (int) ScreenUtil.dp2Px(60);
    private Paint mPaint;
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (RADIUS_SIZE + PADDING) * 2;
        int height = (RADIUS_SIZE + PADDING) * 2;
        // 根据计算尺寸与期望尺寸，算出最终的尺寸
        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.GRAY);
        canvas.drawCircle(RADIUS_SIZE + PADDING , RADIUS_SIZE + PADDING, RADIUS_SIZE, mPaint);
    }
}
