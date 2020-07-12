package com.example.androidviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawTextView extends View {
    public static final String TEXT = "abab";
    public static final int RADIUS = 200;
    private Paint.FontMetrics fontMetrics;
    private static final String CONTENT = "《百年孤独》，是哥伦比亚作家加夫列尔·加西亚·马尔克斯的作品，“魔幻现实主义”的代表作，在世界上享有盛誉。作者也因此获得1982年诺贝尔文学奖，瑞典皇家学院的颁奖理由是：“像其他重要的拉丁美洲作家一样，马尔克斯永远为弱小贫穷者请命，而反抗内部的压迫与外来的剥削。";
    private Bitmap avatar;

    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint paint;
    private Paint textPaint;
    // 测量出来的文字范围
    private Rect textBoundRect;

    private Rect multiLineTextBoundRect;
    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(22 * 3);
        paint.setTextAlign(Paint.Align.CENTER);

        textBoundRect = new Rect();

        fontMetrics = paint.getFontMetrics();

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(12 * 3);
        multiLineTextBoundRect = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCenterText(canvas);
        // 平移画布
        canvas.translate(0, RADIUS * 2 + 40);

        drawMultiLine(canvas);
    }

    private void drawCenterText(Canvas canvas) {
        // 绘制圆形
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(21);
        canvas.drawCircle(getWidth() / 2, RADIUS, RADIUS, paint);

        // 绘制圆弧
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.RED);
        canvas.drawArc(getWidth() / 2 - RADIUS, 0, getWidth() / 2 + RADIUS, 2 * RADIUS, 270, 240, false, paint);


        // 绘制文字居中
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
        paint.getTextBounds(TEXT, 0, TEXT.length(), textBoundRect);
        // 方式1：使用textBound测量当前文字的范围，适合文本不变情况的绘制，不然文本的位置可能会上下晃动
//        canvas.drawText(TEXT, getWidth() / 2, RADIUS - (textBoundRect.centerY()), paint);

        // 方式2：使用FontMetrics修正，该类给出了一行文本的baseLine、ascent、descent---这些值本身带正负，居中为初始的居中值 - （descent + ascent）/ 2
        canvas.drawText(TEXT, getWidth() / 2, RADIUS - (int) (fontMetrics.descent + fontMetrics.ascent) / 2, paint);

    }

    /**
     * staticLayout，从当前canvas的（0，0）开始绘制文本，可绘制多行
     * Paint.getTextBound获取单行最后一个文字的下标
     * @param canvas
     */
    private void drawMultiLine(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        Rect rect = new Rect(getWidth() - 50, 50, getWidth(), 100);
        canvas.drawRect(rect, paint);

        // 绘制多行文本的方式1：StaticLayout，从（0,0）开始绘制所有文本，自动换行
//        StaticLayout staticLayout = new StaticLayout(CONTENT, textPaint, getWidth(), Alignment.ALIGN_NORMAL, 1f, 0, false);
//        staticLayout.draw(canvas);

        // 单独根据getTextBounds绘制每行文本
//        int endOfLineIndex = -1;
//        int startIndex = 0;
//        int mCurrentTop = 0;
//        endOfLineIndex = textPaint.breakText(CONTENT, startIndex, CONTENT.length(), true, getWidth(), null);
//        Log.i("WWS", "startIndex = " + startIndex + " endOfLineIndex = " + endOfLineIndex);
//        canvas.drawText(CONTENT, startIndex, endOfLineIndex, 0, mCurrentTop, textPaint);
//
//        mCurrentTop += textPaint.getFontSpacing();
//        startIndex = endOfLineIndex;
//        endOfLineIndex += textPaint.breakText(CONTENT, startIndex, CONTENT.length(), true, getWidth(), null);
//        Log.i("WWS", "startIndex = " + startIndex + " endOfLineIndex = " + endOfLineIndex);
//        canvas.drawText(CONTENT, startIndex, endOfLineIndex, 0, mCurrentTop, textPaint);
//
//
//        mCurrentTop += textPaint.getFontSpacing();
//        startIndex = endOfLineIndex;
////        endOfLineIndex += textPaint.breakText(CONTENT, startIndex, CONTENT.length(), true, getWidth(), null);
//        Log.i("WWS", "startIndex = " + startIndex + " endOfLineIndex = " + endOfLineIndex);
//        endOfLineIndex += textPaint.breakText(CONTENT, startIndex, CONTENT.length(), true, rect.left, null);
//        canvas.drawText(CONTENT, startIndex, endOfLineIndex, 0, mCurrentTop, textPaint);
//
//        mCurrentTop += textPaint.getFontSpacing();
//        startIndex = endOfLineIndex;
//        endOfLineIndex += textPaint.breakText(CONTENT, startIndex, CONTENT.length(), true, getWidth(), null);
//        Log.i("WWS", "startIndex = " + startIndex + " endOfLineIndex = " + endOfLineIndex);
//        canvas.drawText(CONTENT, startIndex, endOfLineIndex, 0, mCurrentTop, textPaint);

        int endOfLineIndex = -1;
        int mCurrentTop = 0;
        int startIndex = 0;
        int currentLineCount = 0;
        Log.i("WWS", "startIndex = " + startIndex + " length = " + CONTENT.length());
        while (startIndex < (CONTENT.length() - 1)) {
            // 这里要判断完整的
            currentLineCount = getCurrentLineCount(startIndex, getWidth());
            textPaint.getTextBounds(CONTENT, startIndex, currentLineCount + endOfLineIndex, multiLineTextBoundRect);

            if (isInsideRect(rect, mCurrentTop)) {
                // 在图片范围内，该行的最大宽度调整为图片的left
                currentLineCount = getCurrentLineCount(startIndex, rect.left);
                canvas.drawText(CONTENT, startIndex, currentLineCount + endOfLineIndex, 0, mCurrentTop, textPaint);
            } else {
                // 不在图片的范围，该行直接绘制
                canvas.drawText(CONTENT, startIndex, currentLineCount + endOfLineIndex, 0, mCurrentTop, textPaint);
            }
            endOfLineIndex += currentLineCount;
            Log.i("WWS", "startIndex = " + startIndex + " endOfLineIndex = " + endOfLineIndex);
            if (endOfLineIndex != startIndex) {
                mCurrentTop += textPaint.getFontSpacing();
            }
            startIndex = endOfLineIndex;
        }
        canvas.translate(0, mCurrentTop);
    }

    private boolean isInsideRect(Rect rect, int mCurrentTop) {
        return ((mCurrentTop + multiLineTextBoundRect.top) >= rect.top && ((mCurrentTop + multiLineTextBoundRect.top) <= rect.bottom)) || ((mCurrentTop + multiLineTextBoundRect.bottom) > rect.top && (mCurrentTop + multiLineTextBoundRect.bottom) <= rect.bottom);
    }

    private int getCurrentLineCount(int startIndex, int width) {
        return textPaint.breakText(CONTENT, startIndex, CONTENT.length(), true, width, null);
    }

}
