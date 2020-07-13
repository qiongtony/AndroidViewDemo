package com.example.androidviewdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.androidviewdemo.util.BitmapUtil;
import com.example.androidviewdemo.util.ScreenUtil;


/**
 * Camera的使用
 * 调用Camera.applyToCanvas(Canvas)以当前画布的位置进行绘制后图形的投影效果
 * 所以坐标系包含三个
 * 图形显示的坐标系、Canvas的坐标系、Camera的坐标系---Canvas和Camera都是相对的坐标系，图形显示的才是绝对坐标系即屏幕的坐标系
 * applyToCanvas，让Camera移动到Canvas坐标系的位置
 * 图形绘制在Canvas相对坐标系下-》图形的绝对坐标系-》Camera的相对坐标系进行投影
 * 所以applyToCanvas的意思是把Camera的坐标系移到和当前Canvas坐标系重合的位置吧？
 */
public class CameraView extends View {
    private Paint paint;

    private Bitmap avatarBitmap;
    private Camera camera;

    public CameraView(Context context, @NonNull AttributeSet attrs){
        super(context, attrs);
    }

    {
        avatarBitmap = BitmapUtil.getBitmap(getContext());

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        camera = new Camera();
        // 设置投影Z轴的位置，首先为Z轴的负值，负值越小投影位置越远，投影后的图形越小
        // 单位为英寸，1英寸=72像素，需要使用转换为dp的值
        camera.setLocation(0, 0, - ScreenUtil.dp2Px(2));
        // 旋转45度
        camera.rotateX(45);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        canvas.translate(100 + avatarBitmap.getWidth() / 2, 100 + avatarBitmap.getHeight() / 2);
        // 上半部分
        // apply后以当前canvas的画布位置进行投影
        canvas.save();
        canvas.translate(-(100 + avatarBitmap.getWidth() / 2), -(100 + avatarBitmap.getHeight() / 2));
        canvas.clipRect(100 , 100, 100 + avatarBitmap.getWidth(), 100 + avatarBitmap.getHeight() / 2);
        canvas.drawBitmap(avatarBitmap, 100, 100, paint);

        canvas.restore();
        camera.applyToCanvas(canvas);
        canvas.translate(-(100 + avatarBitmap.getWidth() / 2), -(100 + avatarBitmap.getHeight() / 2));
        canvas.clipRect(100 , 100 + avatarBitmap.getHeight() / 2, 100 + avatarBitmap.getWidth(), 100 + avatarBitmap.getHeight());
        canvas.drawBitmap(avatarBitmap, 100, 100, paint);
        canvas.restore();
    }
}
