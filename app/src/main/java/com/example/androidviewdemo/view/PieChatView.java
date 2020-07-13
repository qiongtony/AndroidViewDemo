package com.example.androidviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidviewdemo.util.ScreenUtil;

import java.util.Random;

/**
 * 根据每个饼占的角度算出和颜色，画出该部分圆弧；
 * 遇到需要移动的部分时，根据圆弧的中心度数，算出移动结束点的x值和y值
 */
public class PieChatView extends View {
    /**
     * 饼图的半径
     */
    private static final int RADIUS = (int) ScreenUtil.dp2Px(100);
    private static final int[] RADIUSES = new int[]{60, 40, 90, 30, 140};
    private static final int PROTRUDING_LENGTH = (int) ScreenUtil.dp2Px(20);
    private static final int[] COLORS = new int[]{Color.parseColor("#FF195C52"
    ), Color.parseColor("#FFE53935"), Color.parseColor("#FFD81B60"), Color.parseColor("#FF3949AB")
            , Color.parseColor("#FFC0CA33")};
    private Paint paint;
    private int startAngle;
    // 画饼图的矩形
    private RectF rectF;

    public PieChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectF = new RectF();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.set(w / 2 - RADIUS, h / 2 - RADIUS, w / 2 + RADIUS, h / 2 + RADIUS);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startAngle = 0;
        int randomIndex = new Random().nextInt(RADIUSES.length);
        for (int i = 0; i < RADIUSES.length; i++) {
            paint.setColor(COLORS[i]);
            if (randomIndex == i){
                canvas.save();
                int centerRadius = startAngle + RADIUSES[i] / 2;
                canvas.translate((float)Math.cos(Math.toRadians(centerRadius)) * PROTRUDING_LENGTH, (float)Math.sin(Math.toRadians(centerRadius)) * PROTRUDING_LENGTH);
            }
            canvas.drawArc(rectF, startAngle, RADIUSES[i], true, paint);
            if (randomIndex == i){
                canvas.restore();
            }
            startAngle += RADIUSES[i];
        }
    }
}
