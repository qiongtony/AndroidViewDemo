package com.example.androidviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidviewdemo.util.ScreenUtil;

/**
 * 自定义仪表盘View
 * 包含三部分：
 * 1、圆弧：根据圆的半径以及仪表盘的度数进行绘制，起点为：90 - （360 - 度数） / 2 + （360 - 度数），上一部分求的是尾部在坐标系下的度数，
 * 2、刻度：使用PathDashPathEffect绘制，根据刻度的数量以及仪表盘的周长，计算出每个刻度的距离，（周长 - 刻度的宽）/ （刻度的数量 - 1）,前一部分是由于最后一个刻度也需要包含在仪表盘内，数量-1是由于两两之间才有一个间距
 * 3、直线：以圆心为起点，位置根据直线在坐标系下的sin和cos值来计算
 */
public class DashboardView extends View {
    /**
     * 仪表盘的角度
     */
    private static final int DASH_ANGLE = 240;
    /**
     * 仪表盘刻度的数量
     */
    private static final int DASH_LINE_COUNT = 30;
    /**
     * 直线的长度
     */
    private static final int LINE_LENGTH = (int) ScreenUtil.dp2Px(80);
    public static final int RADIUS = (int) ScreenUtil.dp2Px(100);

    private Paint paint;
    private Path path;
    private RectF rectF;
    private PathMeasure pathMeasure;
    private PathDashPathEffect pathDashPathEffect;
    private Path dashPath;
    private Point linePoint;

    public DashboardView(Context context) {
        super(context);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();
        rectF = new RectF();
        pathMeasure = new PathMeasure();
        dashPath = new Path();
        linePoint = new Point();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.reset();
        // 添加
        rectF.set(w / 2 - RADIUS, h / 2 - RADIUS, w / 2 + RADIUS, h / 2 + RADIUS);
        float startAngle = 90 + (360 - DASH_ANGLE) / 2;
        path.addArc(rectF, startAngle, DASH_ANGLE);

        // 测量高度
        pathMeasure.setPath(path, false);

        dashPath.reset();
        dashPath.addRect(0, 0, ScreenUtil.dp2Px(1), ScreenUtil.dp2Px(5), Path.Direction.CW);
        pathDashPathEffect = new PathDashPathEffect(dashPath, (pathMeasure.getLength() - 2) / (DASH_LINE_COUNT - 1), 0, PathDashPathEffect.Style.ROTATE);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制圆弧
        canvas.drawPath(path, paint);

        // 绘制刻度
        paint.setPathEffect(pathDashPathEffect);
        canvas.drawPath(path, paint);
        paint.setPathEffect(null);

        // 绘制直线，x轴的位置为原点+cos(弧度) * 长度；y轴的位置为远点+sin(弧度) * 长度
        linePoint.x = (int) (getWidth() / 2 + (Math.cos(Math.toRadians(240)) * LINE_LENGTH));
        linePoint.y = (int) (getHeight() / 2 + Math.sin(Math.toRadians(240)) * LINE_LENGTH);
        Log.i("WWS", "point = " + linePoint.toString());
        canvas.drawLine(getWidth() / 2, getHeight() / 2, linePoint.x, linePoint.y, paint);
    }
}
